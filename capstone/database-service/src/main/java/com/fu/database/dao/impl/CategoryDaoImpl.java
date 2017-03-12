package com.fu.database.dao.impl;

import com.fu.database.dao.CategoryDao;
import com.fu.database.entity.Category;
import com.fu.database.entity.CategoryProduct;
import com.fu.database.entity.Product;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 02/11/2016.
 */
@Repository
public class CategoryDaoImpl extends GenericDaoImpl<Category, Integer> implements CategoryDao {

    private static final Logger LOG = Logger.getLogger(CategoryDaoImpl.class);

    @Override
    public List<Category> getCategoryByName(String name) {
        LOG.info("[getIdCategoryByName] Start:");
        LOG.info("[getIdCategoryByName] End");
        return getEntityManager().createNativeQuery("SELECT * FROM category WHERE LOWER(name) REGEXP :name1 AND LOWER(name) NOT REGEXP :name2 AND LOWER(name) NOT REGEXP :name3", Category.class)
                .setParameter("name1", name.toLowerCase())
                .setParameter("name2", " " + name.toLowerCase())
                .setParameter("name3", name.toLowerCase() + " ")
                .getResultList();
    }

    @Override
    public List<Category> getAllCategory() {
        LOG.info("[getAllCategory] Start:");
        LOG.info("[getAllCategory] End");
        return getEntityManager()
                .createQuery("SELECT c FROM Category c ", Category.class)
                .getResultList();
    }

    @Override
    public List<Category> getAll(int firstResult, int maxResult) {
        LOG.info(new StringBuilder("[getAll] Start: firstResult = ").append(firstResult)
                .append(", maxResult = ").append(maxResult));
        LOG.info("[getAll] End");
        return getEntityManager()
                .createQuery("SELECT c FROM Category c ", Category.class)
                .setFirstResult(firstResult)
                .setMaxResults(maxResult)
                .getResultList();
    }

    @Override
    public List<Category> checkDuplicateName(String name) {
        LOG.info("[checkDuplicateName] Start:");
        LOG.info("[checkDuplicateName] End");
        return getEntityManager()
                .createQuery("SELECT c FROM Category c WHERE c.name=:name", Category.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public void deleteCategory(int id) {
        LOG.info("[deleteFloor] Start:");
        LOG.info("[deleteFloor] End");
        getEntityManager().createQuery("DELETE FROM Category c " +
                "WHERE c.id=:id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public List<Product> getProductInCate(int categoryId) {
        LOG.info("[getProductInCate] Start:");
        LOG.info("[getProductInCate] End");
        return getEntityManager().createQuery(String.valueOf(new StringBuilder("SELECT p FROM ")
                .append(Product.class.getSimpleName())
                .append(" p ")
                .append("WHERE p.id IN (SELECT cp.categoryProductKey.productId FROM ")
                .append(CategoryProduct.class.getSimpleName())
                .append(" cp WHERE cp.categoryProductKey.categoryId = :categoryId)")))
                .setParameter("categoryId", categoryId)
                .getResultList();
    }

    @Override
    public boolean addProductInCate(long[] listProductId) {
        return false;
    }

    @Override
    public List<Category> getCategoryOfProduct(long productId) {
        LOG.info("[getCategoryOfProduct] Start:");
        LOG.info("[getCategoryOfProduct] End");
        return getEntityManager().createQuery(String.valueOf(new StringBuilder("SELECT c FROM ")
                .append(Category.class.getSimpleName())
                .append(" c ")
                .append("WHERE c.id IN (SELECT cp.categoryProductKey.categoryId FROM ")
                .append(CategoryProduct.class.getSimpleName())
                .append(" cp WHERE cp.categoryProductKey.productId = :productId)")))
                .setParameter("productId", productId)
                .getResultList();
    }
}
