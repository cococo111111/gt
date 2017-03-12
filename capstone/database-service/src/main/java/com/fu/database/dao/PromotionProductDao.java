package com.fu.database.dao;

import com.fu.database.entity.PromotionProduct;
import com.fu.database.entity.PromotionProductKey;

/**
 * Created by manlm on 9/21/2016.
 */
public interface PromotionProductDao extends GenericDao<PromotionProduct, PromotionProductKey> {

    boolean deletePromotionProduct(long promotionId, long productId);

    boolean deleteAllProductByPromotionId(long promotionId);
}
