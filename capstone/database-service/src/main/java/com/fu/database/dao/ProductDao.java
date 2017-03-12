package com.fu.database.dao;

import com.fu.database.entity.Area;
import com.fu.database.entity.Category;
import com.fu.database.entity.Product;
import com.fu.database.model.ProductApi;
import com.fu.database.model.RelatingModel;

import java.util.List;

/**
 * Created by manlm on 9/21/2016.
 */
public interface ProductDao extends GenericDao<Product, Long> {

    Product getProductById(long productId);

    List<Product> getProductBySearchName(String name, int positionInResult, int maxShowResult);

    List<Product> getProductInCart(List<Long> listProductId);

    List<Product> getPromotionBaseOnWeightHistory(String userId, int positionInResult, int maxShowResult);

    List<ProductApi> getProductForApi();

    List<Product> getSuggestProductById(long productId);

    boolean checkSuggestProductById(long productId);

    List<Product> getAllProduct();

    List<Product> checkDuplicateCode(String code);

    long getIdByCode(String code);

    List<Product> getNewProduct();

    List<Product> getProductNotSuggestById(long productId);

    boolean deleteSuggest(long productId, long productSuggestId);

    List<Product> getAll(int firstResult, int maxResult);

    void updateAreaInProduct(Area area, String floorName);

    void deleteAreaInProduct(Area area);

    void updateFloorInProduct(Area area, String floorName);

    List<Product> getProductInNewestPromotion();

    List<Product> checkDuplicateName(String name);

    List<RelatingModel> getSuggestProductAndWeight(long productId);
}
