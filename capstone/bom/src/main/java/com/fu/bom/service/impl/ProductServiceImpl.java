package com.fu.bom.service.impl;

import com.fu.bom.service.ProductService;
import com.fu.bom.utils.Constant;
import com.fu.common.util.CommonUtil;
import com.fu.common.util.ImageUtil;
import com.fu.database.dao.*;
import com.fu.database.entity.*;
import com.fu.database.model.RelatingModel;
import com.fu.nlp.model.Entry;
import com.fu.nlp.service.NaturalLanguageProcessingService;
import com.fu.storage.service.S3Service;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 10/10/2016.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    private final AreaDao areaDao;

    private final PromotionDao promotionDao;

    private final S3Service s3Service;

    private final CategoryDao categoryDao;

    private static final Logger LOG = Logger.getLogger(ProductServiceImpl.class);

    private final NaturalLanguageProcessingService naturalLanguageProcessingService;

    private final RelatingProductDao relatingProductDao;

    private final CategoryProductDao categoryProductDao;

    private final FloorDao floorDao;

    @Autowired
    public ProductServiceImpl(ProductDao productDao, AreaDao areaDao, PromotionDao promotionDao, S3Service s3Service, CategoryDao categoryDao, NaturalLanguageProcessingService naturalLanguageProcessingService, RelatingProductDao relatingProductDao, CategoryProductDao categoryProductDao, FloorDao floorDao) {
        this.productDao = productDao;
        this.areaDao = areaDao;
        this.promotionDao = promotionDao;
        this.s3Service = s3Service;
        this.categoryDao = categoryDao;
        this.naturalLanguageProcessingService = naturalLanguageProcessingService;
        this.relatingProductDao = relatingProductDao;
        this.categoryProductDao = categoryProductDao;
        this.floorDao = floorDao;
    }

    @Override
    public boolean addProduct(String code, String name, long price, String imgUrl, String details, int areaId, int[] listCategory, String imgThumbNail) {
        LOG.info("[addProduct] Start:");
        if (productDao.checkDuplicateCode(code).isEmpty()) {
            Product product = new Product();
            product = setAreaInProduct(product, areaId);
            product.setCode(code);
            product.setName(name);
            product.setPrice(price);
            product.setImgUrl(imgUrl);
            product.setDetails(details);
            product.setLastUpdate(System.currentTimeMillis());
            product.setImgPromotionUrl(imgThumbNail);
            product.setStatus(true);
            if (addNameNaturalLanguage(name)) {
                try {
                    productDao.insert(product);
                    if (listCategory != null) {
                        CategoryProduct categoryProduct = new CategoryProduct();
                        for (int aListCategory : listCategory) {
                            categoryProduct.setCategoryProductKey(aListCategory, product.getId());
                            categoryProductDao.insert(categoryProduct);
                        }
                    }
                    return true;
                } catch (RuntimeException ex) {
                    deleteNameNaturalLanguage(name);
                    LOG.error("[addProduct] RuntimeException: ", ex);
                }
            }
        }
        LOG.info("[addProduct] End:");
        return false;
    }

    @Override
    public boolean updateProduct(long id, String code, String name, long price, String imgUrl, String details, int areaId, int[] listCategory, String thumbNailUrl) {
        //kiem tra trung voi cai cu ko
        LOG.info("[updateProduct] Start:");
        Product product = productDao.getProductById(id);
        if (product.getName().equals(name)) {
            try {
                product = setAreaInProduct(product, areaId);
                product.setName(name);
                product.setPrice(price);
                product.setImgUrl(imgUrl);
                product.setDetails(details);
                product.setLastUpdate(System.currentTimeMillis());
                product.setImgPromotionUrl(thumbNailUrl);
                product.setStatus(true);
                productDao.update(product);
                if (listCategory != null) {
                    categoryProductDao.deleteCategoryRelatingProduct(id);
                    for (int aListCategory : listCategory) {
                        CategoryProduct categoryProduct = new CategoryProduct();
                        categoryProduct.setCategoryProductKey(aListCategory, product.getId());
                        categoryProductDao.insert(categoryProduct);
                    }
                } else {
                    categoryProductDao.deleteCategoryRelatingProduct(id);
                }
            } catch (RuntimeException ex) {
                LOG.error("[update] Error: update Product ", ex);
                return false;
            }
        } else {
            if (productDao.checkDuplicateName(name).isEmpty()) {
                String oldName = product.getName();
                product = setAreaInProduct(product, areaId);

                product.setName(name);
                product.setPrice(price);
                product.setImgUrl(imgUrl);
                product.setDetails(details);
                product.setLastUpdate(System.currentTimeMillis());
                product.setStatus(true);
                if (updateNameNaturalLanguage(oldName, name)) {
                    try {
                        productDao.update(product);
                        if (listCategory != null) {
                            categoryProductDao.deleteCategoryRelatingProduct(id);
                            CategoryProduct categoryProduct = new CategoryProduct();
                            for (int aListCategory : listCategory) {
                                categoryProduct.setCategoryProductKey(aListCategory, product.getId());
                                categoryProductDao.insert(categoryProduct);
                            }
                        } else {
                            categoryProductDao.deleteCategoryRelatingProduct(id);
                        }
                    } catch (RuntimeException ex) {
                        LOG.error("[update] Error: update Product ", ex);
                        updateNameNaturalLanguage(name, oldName);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public List<Product> getAllProduct() {
        LOG.info("[getAllProduct] Start:");
        LOG.info("[getAllProduct] End");
        return productDao.getAllProduct();

    }

    @Override
    public Product getProductById(long id) {
        LOG.info("[getProductById] Start:");
        LOG.info("[getProductById] End");
        return productDao.getById(id);
    }

    @Override
    public List<Promotion> getPromotionByProductId(long id) {
        LOG.info("[getPromotionByProductId] Start:");
        LOG.info("[getPromotionByProductId] End");
        return promotionDao.getPromotionByProductId(id);
    }

    @Override
    public List<Product> getSuggestProductById(long productId) {
        LOG.info("[getSuggestProductById] Start:");
        LOG.info("[getSuggestProductById] End");
        return productDao.getSuggestProductById(productId);
    }

    @Override
    public boolean addProduct(Product product) {
        LOG.info("[addProduct] Start:");
        try {
            productDao.insert(product);
            return true;
        } catch (RuntimeException ex) {
            LOG.error("[addProduct] Error: addProduct Product:" + product.getName(), ex);
        }
        LOG.info("[addProduct] End");
        return false;
    }

    @Override
    public boolean updateProduct(Product product) {
        LOG.info("[updateProduct] Start:");
        try {
            productDao.update(product);
            return true;
        } catch (RuntimeException ex) {
            LOG.error("[updateProduct] Error: update Product:" + product.getName(), ex);
        }
        LOG.info("[updateProduct] End");
        return false;
    }

    @Override
    public long getIdProductByCode(String code) {
        LOG.info("[getIdProductByCode] Start:");
        LOG.info("[getIdProductByCode] End");
        return productDao.getIdByCode(code);
    }

    @Override
    public List<Product> getNewProduct() {
        LOG.info("[getIdProductByCode] Start:");
        LOG.info("[getIdProductByCode] End");
        return productDao.getNewProduct();
    }

    @Override
    public String[] imgUrlInServer(MultipartFile fileImage, String imgUrlExist, String thumbNailUrlExist, String codeProduct) {
        LOG.info("[imgUrlInServer] Start:");
        String[] imageLink = new String[2];
        String[] datatype = fileImage.getContentType().split("/");
        if (!fileImage.isEmpty()) {

            try {
                byte[] arrByte = fileImage.getBytes();
                imageLink[0] = s3Service.uploadObject("s3://manlm/" + codeProduct + "." + datatype[1], arrByte, codeProduct + "." + datatype[1]);
                byte[] arrByteThumbNail = ImageUtil.generateThumbNail(imageLink[0]);
                imageLink[1] = s3Service.uploadObject("s3://manlm/" + codeProduct + Constant.PROMOTION_EXTENSION, arrByteThumbNail, codeProduct + Constant.PROMOTION_EXTENSION);
            } catch (IOException e) {
                LOG.error("[imgUrlInServer] Error", e);
            }
        } else {
            imageLink[0] = imgUrlExist;
            imageLink[1] = thumbNailUrlExist;
        }
        LOG.info("[imgUrlInServer] End");
        return imageLink;
    }

    @Override
    public boolean addNameNaturalLanguage(String name) {
        LOG.info("[addNameNaturalLanguage] Start:");
        Entry entry = new Entry();
        entry.setValue(name);

        List<String> synonyms = new ArrayList<>();
        synonyms.add(name);
        synonyms.add(CommonUtil.stripAccents(name));
        entry.setSynonyms(synonyms);

        List<Entry> entries = new ArrayList<>();
        entries.add(entry);
        try {
            naturalLanguageProcessingService.addEntriesToEntity(entries, Constant.ENTRIES_PRODUCT);
            return true;
        } catch (RuntimeException ex) {
            LOG.error("[addNameNaturalLanguage] Error:", ex);
        }
        LOG.info("[addNameNaturalLanguage] End");
        return false;
    }

    @Override
    public List<Category> getAllCategory() {
        LOG.info("[getAllCategory] Start:");
        LOG.info("[getAllCategory] End");
        return categoryDao.getAllCategory();

    }

    @Override
    public boolean deleteProduct(long id) {
        LOG.info("[deleteProduct] Start");
        Product product = productDao.getProductById(id);
        if (product.isStatus()) {
            product.setStatus(false);
        } else {
            product.setStatus(true);
        }
        try {
            productDao.update(product);
            return true;
        } catch (RuntimeException ex) {
            LOG.error("[deleteProduct] Error: addProduct Product:" + product.getName(), ex);
        }
        LOG.info("[deleteProduct] End");
        return false;
    }

    @Override
    public List<Product> getSuggestionById(long id) {
        LOG.info("[getSuggestionById] Start");
        LOG.info("[getSuggestionById] End");
        return productDao.getSuggestProductById(id);

    }

    @Override
    public List<Product> getNotSuggestionById(long id) {
        LOG.info("[getNotSuggestionById] Start:");
        LOG.info("[getNotSuggestionById] End");
        return productDao.getProductNotSuggestById(id);
    }

    @Override
    public boolean deleteSuggest(long productId, long productSuggestId) {
        LOG.info("[deleteSuggest] Start:");
        LOG.info("[deleteSuggest] End");
        productDao.deleteSuggest(productId, productSuggestId);
        return true;
    }

    @Override
    public boolean addSuggest(long productId, long productSuggestId, long weightSuggest) {
        LOG.info("[addSuggest] Start:");
        RelatingProduct relatingProduct = new RelatingProduct();
        RelatingProductKey relatingProductKey = new RelatingProductKey();
        relatingProductKey.setProductId1(productId);
        relatingProductKey.setProductId2(productSuggestId);
        relatingProduct.setRelatingProductKey(relatingProductKey);
        relatingProduct.setWeight(weightSuggest);
        try {
            relatingProductDao.insert(relatingProduct);
            return true;
        } catch (RuntimeException ex) {
            LOG.error("[addSuggest] Error", ex);
        }
        LOG.info("[addSuggest] End");
        return false;
    }

    @Override
    public boolean updateNameNaturalLanguage(String oldName, String newName) {
        LOG.info("[updateNameNaturalLanguage] Start:");
        if (!oldName.equals(newName)) {
            Entry entry = new Entry();
            entry.setValue(newName);
            List<String> synonyms = new ArrayList<>();
            synonyms.add(newName);
            synonyms.add(CommonUtil.stripAccents(newName));
            entry.setSynonyms(synonyms);
            List<Entry> entries = new ArrayList<>();
            entries.add(entry);
            List<String> listName = new ArrayList<>();
            listName.add(oldName);
            try {
                naturalLanguageProcessingService.deleteEntries(listName, Constant.ENTRIES_PRODUCT);
                naturalLanguageProcessingService.addEntriesToEntity(entries, Constant.ENTRIES_PRODUCT);
                return true;
            } catch (RuntimeException ex) {
                LOG.error("[updateNameNaturalLanguage] Error", ex);
            }

        }
        LOG.info("[updateNameNaturalLanguage] End");
        return false;
    }

    @Override
    public void deleteNameNaturalLanguage(String name) {
        LOG.info("[deleteNameNaturalLanguage] End");
        List<String> listName = new ArrayList<>();
        listName.add(name);
        naturalLanguageProcessingService.deleteEntries(listName, Constant.ENTRIES_PRODUCT);
        LOG.info("[deleteNameNaturalLanguage] End");
    }

    private Product setAreaInProduct(Product product, int areaId) {
        LOG.info("[setAreaInProduct] Start");
        Area area;
        if (areaId != 0) {
            area = areaDao.getById(areaId);
            product.setAreaId(area.getId());
            product.setAreaName(area.getName());
            product.setAreaWeight(area.getWeight());
            product.setAreaLocation(floorDao.getById(area.getFloorId()).getName());
        } else {
            product.setAreaId(0);
            product.setAreaLocation("");
        }
        LOG.info("[setAreaInProduct] End");
        return product;
    }

    @Override
    public List<RelatingModel> getSuggestProductAndWeight(long product) {
        LOG.info("[getSuggestProductById] Start:");
        LOG.info("[getSuggestProductById] End");
        return productDao.getSuggestProductAndWeight(product);

    }
}
