package com.fu.bom.service.impl;

import com.fu.bom.service.SynonymService;
import com.fu.database.dao.SynonymDao;
import com.fu.database.entity.Synonym;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 08/11/2016.
 */
@Service
public class SynonymServiceImpl implements SynonymService {

    private final SynonymDao synonymDao;

    private static final Logger LOG = Logger.getLogger(CategoryServiceImpl.class);

    public SynonymServiceImpl(SynonymDao synonymDao) {
        this.synonymDao = synonymDao;
    }

    @Override
    public boolean addSynonym(String name, int sysnonymId) {
        LOG.info("[addSynonym] Start:");
        if (synonymDao.checkDuplicateName(name).isEmpty()) {
            Synonym synonym = new Synonym();
            synonym.setName(name);
            synonym.setSynonymId(sysnonymId);
            try {
                synonymDao.insert(synonym);
                LOG.info("[addSynonym] End");
                return true;
            } catch (RuntimeException ex) {
                LOG.error("[addSynonym] Error: addSynonym", ex);
            }
        }
        LOG.info("[addSynonym] End");
        return false;
    }

    @Override
    public boolean updateSynonym(int id, String name, int synonymId) {

        LOG.info("[updateSynonym] Start:");
        Synonym synonym = synonymDao.getById(id);
        if (synonym.getName().equals(name)) {
            LOG.info("[updateSynonym] End");
            return true;
        } else {
            if (synonymDao.checkDuplicateName(name).isEmpty()) {
                try {
                    synonym.setName(name);
                    synonym.setSynonymId(synonymId);
                    synonymDao.update(synonym);
                    LOG.info("[updateSynonym] End");
                    return true;
                } catch (RuntimeException ex) {
                    LOG.error("[updateSynonym] Error: updateSynonym  ", ex);
                }
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean deleteSynonym(int id) {
        LOG.info("[deleteSynonym] Start:");
        try {
            synonymDao.deleteSynonym(id);
            LOG.info("[deleteSynonym] End");
            return true;
        } catch (RuntimeException ex) {
            LOG.error("[deleteSynonym] Error: deleteSynonym  ", ex);
        }
        LOG.info("[deleteSynonym] End");
        return false;
    }

    @Override
    public List<Synonym> getAll() {
        LOG.info("[getAll] Start:");
        LOG.info("[getAll] End");
        return synonymDao.getAll();
    }

    @Override
    public boolean updateKeyOriginal(int id, String name) {
        LOG.info("[updateKeyOriginal] Start:");
        Synonym synonym = synonymDao.getById(id);
        if (synonym.getName().equals(name)) {
            return true;
        } else {
            if (synonymDao.checkDuplicateName(name).isEmpty()) {
                try {
                    synonym.setName(name);
                    synonymDao.update(synonym);
                    LOG.info("[updateKeyOriginal] End");
                    return true;
                } catch (RuntimeException ex) {
                    LOG.error("[updateKeyOriginal] Error: updateKeyOriginal  ", ex);
                }
                LOG.info("[updateKeyOriginal] End");
                return false;
            }
        }
        LOG.info("[updateKeyOriginal] End");
        return false;
    }

    @Override
    public List<Synonym> getKeyOriginal() {
        LOG.info("[getKeyOriginal] Start:");
        LOG.info("[getKeyOriginal] End");
        return synonymDao.getAllKeyOriginal();
    }

    @Override
    public List<Synonym> getSynonymByKeyOriginalId(int keyOriginal) {
        LOG.info("[getSynonymByKeyOriginalId] Start:");
        LOG.info("[getSynonymByKeyOriginalId] End");
        return synonymDao.getSynonymByKeyOriginalId(keyOriginal);
    }

    @Override
    public List<Synonym> getSynonym() {
        LOG.info("[getSynonym] Start:");
        LOG.info("[getSynonym] End");
        return synonymDao.getSynonym();
    }

    @Override
    public boolean deleteOriginalWord(int id) {
        LOG.info("[deleteSynonym] Start:");
        try {
            synonymDao.deleteOriginalWord(id);
            LOG.info("[deleteSynonym] End");
            return true;
        } catch (RuntimeException ex) {
            LOG.error("[deleteSynonym] Error: deleteSynonym  ", ex);
        }
        LOG.info("[deleteSynonym] End");
        return false;
    }
}
