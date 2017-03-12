package com.fu.back.service.impl;

import com.fu.back.service.PromotionService;

import com.fu.cache.client.JedisClient;
import com.fu.database.dao.CustomerDao;
import com.fu.database.dao.ProductDao;
import com.fu.database.dao.PromotionDao;
import com.fu.database.entity.Customer;
import com.fu.database.entity.Product;
import com.fu.database.entity.Promotion;
import com.fu.database.model.SaveData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manlm on 12/2/2016.
 */
@Service
public class PromotionServiceImpl implements PromotionService {

    private static final Logger LOG = Logger.getLogger(PromotionServiceImpl.class);

    private final CustomerDao customerDao;

    private final PromotionDao promotionDao;

    private final ProductDao productDao;


    private final JedisClient jedisClient;

    @Autowired
    public PromotionServiceImpl(CustomerDao customerDao, PromotionDao promotionDao, ProductDao productDao,  JedisClient jedisClient) {
        this.customerDao = customerDao;
        this.promotionDao = promotionDao;
        this.productDao = productDao;

        this.jedisClient = jedisClient;
    }

    @Override
    public void notifyPromotion() {
        LOG.info("[notifyPromotion] Start");
        List<Customer> customers = customerDao.getAll();
        String botFbId;


        for (Customer customer : customers) {
            botFbId = customer.getBotFbId();
            if (!"".equals(botFbId)) {
                getCart(botFbId);
            }
        }

        LOG.info("[notifyPromotion] End");
    }

    private void getCart(String botFbId) {
//        LOG.info("[getCart] Start");
//        long productId;
//        List<Promotion> promotionList;
//        long currentTime;
//        long currentTimeEnd;
//        long promotionStart;
//        List<Long> productIds = new ArrayList<>();
//        StringBuilder promotionString;
//        Product product;
//        List<SaveData> saveDatas = (List<SaveData>) jedisClient.get(botFbId + Constant.CART_POST_FIX);
//        for (SaveData saveData : saveDatas) {
//            productId = saveData.getProductId();
//            promotionList = promotionDao.getSpecificPromotionByProductId(productId);
//            for (Promotion promotion : promotionList) {
//                promotionStart = promotion.getStartDate();
//                currentTime = System.currentTimeMillis();
//                currentTimeEnd = currentTime + 23 * 3600 * 1000 + 59 * 60 * 1000 + 59 * 1000;
//                if (currentTime >= promotionStart && currentTimeEnd <= promotionStart) {
//                    productIds.add(productId);
//                }
//            }
//            if (!productIds.isEmpty()) {
//                promotionString = new StringBuilder();
//                for (long promotionProductId : productIds) {
//                    product = productDao.getById(promotionProductId);
//                    promotionString.append(product.getName()).append("\n");
//                }
//                facebookAPIService.sendTextMessage(com.fu.back.utils.Constant.NOTI_PRPOMOTION);
//                facebookAPIService.sendTextMessage(String.valueOf(promotionString));
//            }
//        }
        LOG.info("[getCart] End");
    }
}
