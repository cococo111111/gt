package com.fu.database.dao.impl;

import com.fu.database.dao.PromotionDao;
import com.fu.database.entity.Product;
import com.fu.database.entity.Promotion;
import com.fu.database.entity.PromotionProduct;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by manlm on 9/21/2016.
 */
@Repository
public class PromotionDaoImpl extends GenericDaoImpl<Promotion, Long> implements PromotionDao {

    private static final Logger LOG = Logger.getLogger(PromotionDaoImpl.class);

    @Override
    public List<Promotion> getPromotionByProductId(long productId) {
        LOG.info("[getPromotionByProductId] Start: productId = " + productId);
        LOG.info("[getPromotionByProductId] End");
        return getEntityManager().createQuery(String.valueOf(new StringBuilder("SELECT p FROM ")
                .append(Promotion.class.getSimpleName())
                .append(" p ")
                .append("WHERE p.endDate>:currentTime and p.id IN (SELECT pp.promotionProductKey.promotionId FROM ")
                .append(PromotionProduct.class.getSimpleName())
                .append(" pp WHERE pp.promotionProductKey.productId = :productId)")))
                .setParameter("productId", productId)
                .setParameter("currentTime", System.currentTimeMillis())
                .getResultList();
    }

    @Override
    public List<Promotion> getSpecificPromotionByProductId(long productId) {
        LOG.info("[getPromotionByProductId] Start: productId = " + productId);
        LOG.info("[getPromotionByProductId] End");
        return getEntityManager().createQuery(String.valueOf(new StringBuilder("SELECT p FROM ")
                .append(Promotion.class.getSimpleName())
                .append(" p ")
                .append("WHERE p.endDate>:currentTime and p.id  IN (SELECT pp.promotionProductKey.promotionId FROM ")
                .append(PromotionProduct.class.getSimpleName())
                .append(" pp WHERE pp.promotionProductKey.productId = :productId)")))
                .setParameter("productId", productId)
                .setParameter("currentTime", System.currentTimeMillis())
                .getResultList();
    }

    @Override
    public List<Promotion> getAllPromotion() {
        LOG.info("[getAllPromotion] Start");
        LOG.info("[getAllPromotion] End");
        return getEntityManager().createQuery("SELECT p FROM  Promotion p ", Promotion.class).getResultList();
    }

    @Override
    public List<Product> getProductByPromotionId(long promotionId) {
        LOG.info("[getProductByPromotionId] Start: promotion ID = " + promotionId);
        LOG.info("[getProductByPromotionId] End");
        return getEntityManager().createQuery(String.valueOf(new StringBuilder("SELECT p FROM ")
                .append(Product.class.getSimpleName())
                .append(" p ")
                .append("WHERE p.id IN (SELECT pp.promotionProductKey.productId FROM ")
                .append(PromotionProduct.class.getSimpleName())
                .append(" pp WHERE pp.promotionProductKey.promotionId = :promotionId)")))
                .setParameter("promotionId", promotionId)
                .getResultList();
    }

    @Override
    public List<Product> getProductNotInByPromotionId(long promotionId) {
        LOG.info("[getProductByPromotionId] Start: promotion ID = " + promotionId);
        LOG.info("[getProductByPromotionId] End");
        return getEntityManager().createQuery(String.valueOf(new StringBuilder("SELECT p FROM ")
                .append(Product.class.getSimpleName())
                .append(" p ")
                .append("WHERE p.id NOT IN (SELECT pp.promotionProductKey.productId FROM ")
                .append(PromotionProduct.class.getSimpleName())
                .append(" pp WHERE pp.promotionProductKey.promotionId = :promotionId)")))
                .setParameter("promotionId", promotionId)
                .getResultList();
    }

    @Override
    public boolean detelePromotion(long promotionId) {
        LOG.info("[detelePromotion] Start:");
        LOG.info("[detelePromotion] End");
        getEntityManager().createQuery("DELETE FROM Promotion p " +
                "WHERE p.id=:promotionId")
                .setParameter("promotionId", promotionId)
                .executeUpdate();
        return true;
    }
}
