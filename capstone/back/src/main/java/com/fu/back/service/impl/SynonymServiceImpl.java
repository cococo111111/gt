package com.fu.back.service.impl;

import com.fu.back.service.SynonymService;
import com.fu.common.util.CommonUtil;
import com.fu.database.dao.CategoryDao;
import com.fu.database.dao.ProductDao;
import com.fu.database.dao.SynonymDao;
import com.fu.database.entity.Category;
import com.fu.database.entity.Product;
import com.fu.database.entity.Synonym;
import com.fu.nlp.model.Entry;
import com.fu.nlp.service.AccentizerService;
import com.fu.nlp.service.NaturalLanguageProcessingService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by manlm on 11/5/2016.
 */
@Service
public class SynonymServiceImpl implements SynonymService {

    private static final String ENTRIES_PRODUCT = "Product";

    private static final Logger LOG = Logger.getLogger(SynonymServiceImpl.class);

    private final CategoryDao categoryDao;

    private final ProductDao productDao;

    private final NaturalLanguageProcessingService naturalLanguageProcessingService;

    private final SynonymDao synonymDao;

    @Autowired
    public SynonymServiceImpl(CategoryDao categoryDao, ProductDao productDao, NaturalLanguageProcessingService naturalLanguageProcessingService, SynonymDao synonymDao) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
        this.naturalLanguageProcessingService = naturalLanguageProcessingService;
        this.synonymDao = synonymDao;
    }

    @Override
    public void generateSynonym() {
        LOG.info("[generateSynonym] Start");
        generateCategorySynonym();
        generateProductSynonym();
        LOG.info("[generateSynonym] End");
    }

    private void generateCategorySynonym() {
        LOG.info("[generateCategorySynonym] Start");

        List<Category> categories;
        int categoryStart = 0;
        int noOfRecord = 1000;

        do {
            categories = categoryDao.getAll(categoryStart, noOfRecord);

            for (Category category : categories) {
                calcSynonym(category.getName().toLowerCase());
                categoryStart += noOfRecord;
            }
        }
        while (categories.size() == noOfRecord);
        LOG.info("[generateCategorySynonym] End");
    }

    private void generateProductSynonym() {
        LOG.info("[generateProductSynonym] Start");

        List<Product> products;
        int categoryStart = 0;
        int noOfRecord = 1000;

        do {
            products = productDao.getAll(categoryStart, noOfRecord);

            for (Product product : products) {
                calcSynonym(product.getName().toLowerCase());
                categoryStart += noOfRecord;
            }
        }
        while (products.size() == noOfRecord);
        LOG.info("[generateProductSynonym] End");
    }

    private void calcSynonym(String name) {
        LOG.info("[calcSynonym] Start: name = " + name);
        List<Synonym> synonyms;
        int synonymStart = 0;
        int noOfRecord = 1000;

        Entry entry = new Entry();
        List<Entry> entries = new ArrayList<>();
        List<String> synonymsOfEntry = new ArrayList<>();

        entry.setValue(name);
        synonymsOfEntry.add(name);

        String removeAccentName = CommonUtil.stripAccents(name);
        if (!removeAccentName.equals(name)) {
            synonymsOfEntry.add(removeAccentName);
        }

        List<String> removeAccents;
        Pattern pattern = Pattern.compile("\\s");
        Matcher matcher;
        boolean found;
        do {
            synonyms = synonymDao.getAll(synonymStart, noOfRecord);

            for (Synonym synonym : synonyms) {
                synonymsOfEntry = calcSynonymForAWord(synonymsOfEntry, synonym);
            }

            if (synonymsOfEntry.size() > 2) {
                // add remove accent
                removeAccents = new ArrayList<>();
                for (String s : synonymsOfEntry) {
                    matcher = pattern.matcher(s);
                    // check whitespace
                    if (matcher.find()) {
                        removeAccents.add(CommonUtil.stripAccents(s));
                    }
                }
                synonymsOfEntry.addAll(removeAccents);

                // remove duplicate
                synonymsOfEntry = removeDuplicate(synonymsOfEntry);


            }

            synonymStart += noOfRecord;
        } while (synonyms.size() == noOfRecord);

        if (synonymsOfEntry.size() > 2) {
            entry.setSynonyms(synonymsOfEntry);
            entries.add(entry);
            List<String> names = entries.stream().map(Entry::getValue).collect(Collectors.toList());

            if (naturalLanguageProcessingService.deleteEntries(names, ENTRIES_PRODUCT)) {
                naturalLanguageProcessingService.addEntriesToEntity(entries, ENTRIES_PRODUCT);
            }
        }
        LOG.info("[calcSynonym] End");
    }

    /**
     * Generate synonym for a word
     *
     * @param synonymsOfEntry
     * @param synonym
     * @return
     */
    private List<String> calcSynonymForAWord(List<String> synonymsOfEntry, Synonym synonym) {
        LOG.info("[calcSynonymForAWord] Start: synonym = " + synonym.getName());

        List<String> newSynonymOfEntry = new ArrayList<>();
        List<Synonym> replaceSynonymList;

        String synonymName;
        synonymName = synonym.getName().toLowerCase();

        for (String synonymOfEntry : synonymsOfEntry) {
            if (CommonUtil.matchesWord(synonymName, synonymOfEntry)) {
                replaceSynonymList = synonymDao.getByIdAndSynonymId(synonym.getId(), synonym.getSynonymId());
                newSynonymOfEntry = replaceSynonymInWord(newSynonymOfEntry, synonymName
                        , synonymsOfEntry, synonymOfEntry, replaceSynonymList);
            }
        }
        synonymsOfEntry.addAll(newSynonymOfEntry);

        LOG.info("[calcSynonymForAWord] End");
        return synonymsOfEntry;
    }

    /**
     * replacing
     *
     * @param newSynonymOfEntry
     * @param synonymName
     * @param synonymsOfEntry
     * @param synonymOfEntry
     * @param replaceSynonymList
     * @return
     */
    private List<String> replaceSynonymInWord(List<String> newSynonymOfEntry, String synonymName
            , List<String> synonymsOfEntry, String synonymOfEntry, List<Synonym> replaceSynonymList) {
        LOG.info("[replaceSynonymInWord] Start: synonymName = " + synonymName);

        String newSynonym;
        for (Synonym replaceSynonym : replaceSynonymList) {
            newSynonym = synonymOfEntry.replace(synonymName, replaceSynonym.getName().trim());
            if (!synonymsOfEntry.contains(newSynonym) && !newSynonymOfEntry.contains(newSynonym)
                    && !newSynonymOfEntry.contains(CommonUtil.stripAccents(newSynonym))) {
                newSynonymOfEntry.add(newSynonym);
            }
        }

        LOG.info("[replaceSynonymInWord] End");
        return newSynonymOfEntry;
    }

    private List<String> removeDuplicate(List<String> synonymsOfEntry) {
        LOG.info("[removeDuplicate] Start");
        LinkedHashSet<String> lhs = new LinkedHashSet<>();
        lhs.addAll(synonymsOfEntry);
        synonymsOfEntry.clear();
        synonymsOfEntry.addAll(lhs);
        LOG.info("[removeDuplicate] End");
        return synonymsOfEntry;
    }
}
