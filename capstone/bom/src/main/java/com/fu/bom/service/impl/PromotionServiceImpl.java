package com.fu.bom.service.impl;

import com.fu.bom.service.PromotionService;
import com.fu.bom.utils.Constant;
import com.fu.common.util.DateUtil;
import com.fu.database.dao.PromotionDao;
import com.fu.database.dao.PromotionProductDao;
import com.fu.database.entity.Product;
import com.fu.database.entity.Promotion;
import com.fu.database.entity.PromotionProduct;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 03/11/2016.
 */
@Service
public class PromotionServiceImpl implements PromotionService {

    private final PromotionDao promotionDao;

    private final PromotionProductDao promotionProductDao;

    private static final Logger LOG = Logger.getLogger(ProductServiceImpl.class);

    public PromotionServiceImpl(PromotionDao promotionDao, PromotionProductDao promotionProductDao) {
        this.promotionDao = promotionDao;
        this.promotionProductDao = promotionProductDao;
    }

    @Override
    public List<Promotion> getAllPromotion() {
        return promotionDao.getAllPromotion();
    }

    @Override
    public boolean addPromotion(String name, String details, String discountRate, String startDate, String endDate) {
        LOG.info("[addPromotion] Start:");
        Promotion promotion = new Promotion();
        promotion.setName(name);
        promotion.setDetails(details);
        promotion.setDiscountRate(Integer.parseInt(discountRate));
        promotion.setStartDate(DateUtil.parseMillisecondFromString(startDate, "yyyy-MM-dd"));
        promotion.setEndDate(DateUtil.parseMillisecondFromString(endDate, "yyyy-MM-dd") + Constant.ONEDAY);
        try {
            promotionDao.insert(promotion);
            return true;
        } catch (RuntimeException ex) {
            LOG.error("[addPromotion] RuntimeException: ", ex);
        }
        LOG.info("[addPromotion] End");
        return false;
    }

    @Override
    public List<Product> getProductByPromotionId(long promotionId) {
        LOG.info("[getProductByPromotionId] Start:");
        LOG.info("[getProductByPromotionId] End");
        return promotionDao.getProductByPromotionId(promotionId);

    }

    @Override
    public List<Product> getProductNotInByPromotionId(long promotionId) {
        LOG.info("[getProductNotInByPromotionId] Start:");
        LOG.info("[getProductNotInByPromotionId] End");
        return promotionDao.getProductNotInByPromotionId(promotionId);

    }

    @Override
    public Promotion getPromotionById(long promotionId) {
        LOG.info("[getPromotionById] Start:");
        LOG.info("[getPromotionById] End");
        return promotionDao.getById(promotionId);
    }

    @Override
    public boolean insertPromotionProduct(long promotionId, long productId) {
        LOG.info("[insertPromotionProduct] Start:");
        PromotionProduct promotionProduct = new PromotionProduct();
        promotionProduct.setPromotionProductKey(promotionId, productId);
        try {
            promotionProductDao.insert(promotionProduct);
            return true;
        } catch (RuntimeException ex) {
            LOG.error("[insertPromotionProduct] RuntimeException: ", ex);
        }
        LOG.info("[insertPromotionProduct] End");
        return false;
    }

    @Override
    public boolean deletePromotionProduct(long promotionId, long productId) {
        LOG.info("[deletePromotionProduct] Start:");
        try {
            promotionProductDao.deletePromotionProduct(promotionId, productId);
            return true;
        } catch (RuntimeException ex) {
            LOG.error("[deletePromotionProduct] Error", ex);
        }
        LOG.info("[deletePromotionProduct] End");
        return false;
    }

    @Override
    public boolean deleteAllProductByPromotionId(long promotionId) {
        LOG.info("[deleteAllProductByPromotionId] Start:");
        try {
            promotionProductDao.deleteAllProductByPromotionId(promotionId);
            LOG.info("[deleteAllProductByPromotionId] End");
            return true;
        } catch (RuntimeException ex) {
            LOG.error("[deleteAllProductByPromotionId] Error", ex);
        }
        LOG.info("[deleteAllProductByPromotionId] End");
        return false;
    }

    @Override
    public boolean addAllProductByPromotionId(long promotionId) {
        LOG.info("[addAllProductByPromotionId] Start:");
        int check = 0;
        List<Product> list = getProductNotInByPromotionId(promotionId);
        if (!list.isEmpty()) {
            for (Product product : list) {
                PromotionProduct promotionProduct = new PromotionProduct();
                promotionProduct.setPromotionProductKey(promotionId, product.getId());
                try {
                    promotionProductDao.insert(promotionProduct);
                    check = check + 1;
                } catch (RuntimeException ex) {
                    LOG.error("[insertPromotionProduct] Error", ex);
                }
            }
        }
        if (check == list.size() - 1) {
            LOG.info("[addAllProductByPromotionId] End");
            return true;
        }
        LOG.info("[addAllProductByPromotionId] End");
        return false;
    }

    @Override
    public boolean updatePromotion(String id, String name, String details, String discountRate, String startDate, String endDate) {
        LOG.info("[updatePromotion] Start:");
        Promotion promotion = new Promotion();
        promotion.setId(Long.parseLong(id));
        promotion.setDetails(details);
        promotion.setName(name);
        promotion.setDiscountRate(Integer.parseInt(discountRate));
        promotion.setStartDate(DateUtil.parseMillisecondFromString(startDate, "yyyy-MM-dd"));
        promotion.setEndDate(DateUtil.parseMillisecondFromString(endDate, "yyyy-MM-dd") + Constant.ONEDAY);
        try {
            promotionDao.update(promotion);
            LOG.info("[updatePromotion] End");
            return true;
        } catch (RuntimeException ex) {
            LOG.error("[updatePromotion] Error", ex);
        }
        LOG.info("[updatePromotion] End");
        return false;
    }

    @Override
    public boolean detelePromotion(long promotionId) {
        LOG.info("[detelePromotion] Start:");
        deleteAllProductByPromotionId(promotionId);
        try {
            promotionDao.detelePromotion(promotionId);
            return true;
        } catch (RuntimeException ex) {
            LOG.error("[detelePromotion] Error", ex);
        }
        LOG.info("[detelePromotion] End");
        return false;
    }

    @Override
    public boolean addProductIntoPromotion(long promotionId, long[] listproductId) {
        LOG.info("[insertPromotionProduct] Start:");
        PromotionProduct promotionProduct = new PromotionProduct();
        try {
            for (long aListproductId : listproductId) {
                promotionProduct.setPromotionProductKey(promotionId, aListproductId);
                promotionProductDao.insert(promotionProduct);
            }
            return true;
        } catch (RuntimeException ex) {
            LOG.error("[insertPromotionProduct] Error", ex);
        }
        LOG.info("[insertPromotionProduct] End");
        return false;
    }
}
