package com.fu.bom.service;

import com.fu.database.entity.Product;
import com.fu.database.entity.Promotion;

import java.util.List;

/**
 * Created by Administrator on 03/11/2016.
 */
public interface PromotionService {

    List<Promotion> getAllPromotion();

    boolean addPromotion(String name, String details, String discountRate, String startDate, String endDate);

    List<Product> getProductByPromotionId(long promotionId);

    List<Product> getProductNotInByPromotionId(long promotionId);

    Promotion getPromotionById(long promotionId);

    boolean insertPromotionProduct(long promotionId, long productId);

    boolean deletePromotionProduct(long promotionId, long productId);

    boolean deleteAllProductByPromotionId(long promotionId);

    boolean addAllProductByPromotionId(long promotionId);

    boolean updatePromotion(String id, String name, String details, String discountRate, String startDate, String endDate);

    boolean detelePromotion(long promotionId);

    boolean addProductIntoPromotion(long promotionId, long[] listproductId);
}
