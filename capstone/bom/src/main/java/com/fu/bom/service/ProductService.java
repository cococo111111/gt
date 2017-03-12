package com.fu.bom.service;

import com.fu.database.entity.Category;
import com.fu.database.entity.Product;
import com.fu.database.entity.Promotion;
import com.fu.database.model.RelatingModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by Administrator on 10/10/2016.
 */
public interface ProductService {
    boolean addProduct(String code, String name, long price, String imgUrl, String details, int areaId, int[] categoryId, String thumbNailUrl);

    boolean updateProduct(long id, String code, String name, long price, String imgUrl, String details, int areaId, int[] listCategory, String thumbNailUrl);

    List<Product> getAllProduct();

    Product getProductById(long id);

    List<Promotion> getPromotionByProductId(long id);

    List<Product> getSuggestProductById(long productId);

    boolean addProduct(Product product);

    boolean updateProduct(Product product);

    long getIdProductByCode(String code);

    List<Product> getNewProduct();

    String[] imgUrlInServer(MultipartFile fileImage, String imgUrlExist, String thumbNailUrlExist, String codeProduct);

    boolean addNameNaturalLanguage(String name);

    List<Category> getAllCategory();

    boolean deleteProduct(long id);

    List<Product> getSuggestionById(long id);

    List<Product> getNotSuggestionById(long id);

    boolean deleteSuggest(long productId, long productSuggestId);

    boolean addSuggest(long productId, long productSuggestId, long weightSuggest);

    boolean updateNameNaturalLanguage(String oldName, String newName);

    void deleteNameNaturalLanguage(String name);

    List<RelatingModel> getSuggestProductAndWeight(long product);
}
