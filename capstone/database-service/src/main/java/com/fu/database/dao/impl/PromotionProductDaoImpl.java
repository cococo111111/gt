package com.fu.database.dao.impl;

import com.fu.database.dao.PromotionProductDao;
import com.fu.database.entity.PromotionProduct;
import com.fu.database.entity.PromotionProductKey;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * Created by manlm on 9/21/2016.
 */
@Repository
public class PromotionProductDaoImpl extends GenericDaoImpl<PromotionProduct, PromotionProductKey> implements PromotionProductDao {

    private static final Logger LOG = Logger.getLogger(PromotionProductDaoImpl.class);

    @Override
    public boolean deletePromotionProduct(long promotionId, long productId) {
        LOG.info("[deletePromotionProduct] Start:");
        LOG.info("[deletePromotionProduct] End");
        getEntityManager().createQuery("DELETE FROM PromotionProduct pp " +
                "WHERE pp.promotionProductKey.productId=:productId " +
                "AND pp.promotionProductKey.promotionId=:promotionId")
                .setParameter("productId", productId)
                .setParameter("promotionId", promotionId)
                .executeUpdate();
        return true;
    }
    @Override
    public boolean deleteAllProductByPromotionId(long promotionId) {
        LOG.info("[deleteAllProductByPromotionId] Start:");
        LOG.info("[deleteAllProductByPromotionId] End");
        getEntityManager().createQuery("DELETE FROM PromotionProduct pp " +
                "WHERE pp.promotionProductKey.promotionId=:promotionId")
                .setParameter("promotionId", promotionId)
                .executeUpdate();
        return true;
    }
}
