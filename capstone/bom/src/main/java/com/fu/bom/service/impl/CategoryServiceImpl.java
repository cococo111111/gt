package com.fu.bom.service.impl;

import com.fu.bom.service.CategoryService;
import com.fu.bom.service.ProductService;
import com.fu.database.dao.CategoryDao;
import com.fu.database.dao.CategoryProductDao;
import com.fu.database.entity.Category;
import com.fu.database.entity.CategoryProduct;
import com.fu.database.entity.Product;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 08/11/2016.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;

    private final ProductService productService;

    private final CategoryProductDao categoryProductDao;

    private static final Logger LOG = Logger.getLogger(CategoryServiceImpl.class);

    @Autowired
    public CategoryServiceImpl(CategoryDao categoryDao, ProductService productService, CategoryProductDao categoryProductDao) {
        this.categoryDao = categoryDao;
        this.productService = productService;
        this.categoryProductDao = categoryProductDao;
    }

    @Override
    public boolean addCategory(String name) {
        LOG.info("[addCategory] Start: name = " + name);
        if (categoryDao.checkDuplicateName(name).isEmpty()) {
            Category category = new Category();
            category.setName(name);
            try {
                categoryDao.insert(category);
                return true;
            } catch (RuntimeException ex) {
                LOG.error("[addCategory] RuntimeException: ", ex);
            }
        }
        LOG.info("[addCategory] End");
        return false;
    }

    @Override
    public boolean updateCategory(int id, String name) {
        LOG.info("[updateCategory] Start: name = " + name);
        Category category = categoryDao.getById(id);
        if (category.getName().equals(name)) {
            LOG.info("[updateCategory] End");
            return true;
        } else {
            if (categoryDao.checkDuplicateName(name).isEmpty()) {
                String oldName = category.getName();
                try {
                    category.setName(name);
                    categoryDao.update(category);
                    productService.updateNameNaturalLanguage(oldName, name);
                    return true;
                } catch (RuntimeException ex) {
                    productService.updateNameNaturalLanguage(name, oldName);
                    LOG.error("[updateCategory] RuntimeException: ", ex);
                }
                LOG.info("[updateCategory] End");
                return false;
            }
        }
        LOG.info("[updateCategory] End");
        return false;
    }

    @Override
    public boolean deleteCategory(int categoryId) {
        LOG.info("[deleteCategory] Start: categoryId = " + categoryId);
        try {
            Category category = categoryDao.getById(categoryId);
            categoryProductDao.deleteCategoryProduct(categoryId);
            categoryDao.deleteCategory(categoryId);
            productService.deleteNameNaturalLanguage(category.getName());
            return true;
        } catch (RuntimeException ex) {
            LOG.error("[deleteCategory] RuntimeException: ", ex);
        }
        LOG.info("[deleteCategory] End");
        return false;
    }

    @Override
    public List<Category> getAll() {
        LOG.info("[getAll] Start:");
        LOG.info("[getAll] End");
        return categoryDao.getAllCategory();
    }

    @Override
    public List<Product> getProductInCate(int categoryId) {
        LOG.info("[getProductInCate] Start:");
        LOG.info("[getProductInCate] End");
        return categoryDao.getProductInCate(categoryId);
    }

    @Override
    public boolean addProductInCate(int categoryId, long[] listProductId) {
        LOG.info("[addProductInCate] Start:");
        CategoryProduct categoryProduct = new CategoryProduct();
        try {
            for (long aListProductId : listProductId) {
                categoryProduct.setCategoryProductKey(categoryId, aListProductId);
                categoryProductDao.insert(categoryProduct);
            }
            LOG.info("[addProductInCate] End");
            return true;
        } catch (RuntimeException ex) {
            LOG.error("[addProductInCate] RuntimeException: ", ex);
        }
        LOG.info("[addProductInCate] End");
        return false;
    }

    @Override
    public boolean deleteProductInCate(int categoryId, long productId) {
        LOG.info("[deleteProductInCate] Start: categoryId = " + categoryId);
        try {
            categoryProductDao.deleteProductInCate(categoryId, productId);
            LOG.info("[deleteProductInCate] End");
            return true;
        } catch (RuntimeException ex) {
            LOG.error("[deleteProductInCate] RuntimeException: ", ex);
        }
        LOG.info("[deleteProductInCate] End");
        return false;
    }

    @Override
    public List<Category> getCategoryOfProduct(long productId) {
        LOG.info("[getCategoryOfProduct] Start:");
        LOG.info("[getCategoryOfProduct] End");
        return categoryDao.getCategoryOfProduct(productId);
    }
}
