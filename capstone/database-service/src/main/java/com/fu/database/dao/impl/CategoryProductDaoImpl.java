package com.fu.database.dao.impl;

import com.fu.database.dao.CategoryProductDao;
import com.fu.database.entity.CategoryProduct;
import com.fu.database.entity.CategoryProductKey;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 19/11/2016.
 */
@Repository
public class CategoryProductDaoImpl extends GenericDaoImpl<CategoryProduct, CategoryProductKey> implements CategoryProductDao {

    private static final Logger LOG = Logger.getLogger(CategoryProductDaoImpl.class);

    @Override
    public boolean deleteProductInCate(int categoryId, long productId) {
        LOG.info("[deleteProductInCate] Start:");
        getEntityManager().createQuery("DELETE FROM CategoryProduct cp " +
                "WHERE cp.categoryProductKey.productId=:productId " +
                "AND cp.categoryProductKey.categoryId=:categoryId")
                .setParameter("productId", productId)
                .setParameter("categoryId", categoryId)
                .executeUpdate();
        LOG.info("[deleteProductInCate] End");
        return true;
    }

    @Override
    public List<CategoryProduct> checkExistProductInCate(int categoryId, long productId) {
        LOG.info("[checkExistProductInCate] Start:");
        LOG.info("[checkExistProductInCate] End");
        return getEntityManager().createQuery("SELECT cp FROM CategoryProduct cp " +
                "WHERE cp.categoryProductKey.productId=:productId " +
                "AND cp.categoryProductKey.categoryId=:categoryId", CategoryProduct.class)
                .setParameter("productId", productId)
                .setParameter("categoryId", categoryId)
                .getResultList();

    }

    @Override
    public void deleteCategoryProduct(int categoryId) {
        LOG.info("[deleteCategoryProduct] Start:");
        LOG.info("[deleteCategoryProduct] End");
        getEntityManager().createQuery("DELETE FROM CategoryProduct cp " +
                "WHERE cp.categoryProductKey.categoryId=:categoryId")
                .setParameter("categoryId", categoryId)
                .executeUpdate();
    }

    @Override
    public void deleteCategoryRelatingProduct(long productId) {
        LOG.info("[deleteCategoryProduct] Start:");
        LOG.info("[deleteCategoryProduct] End");
        getEntityManager().createQuery("DELETE FROM CategoryProduct cp " +
                "WHERE cp.categoryProductKey.productId=:productId")
                .setParameter("productId", productId)
                .executeUpdate();
    }
}
