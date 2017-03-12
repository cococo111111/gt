package com.fu.database.dao;

import com.fu.database.entity.Category;
import com.fu.database.entity.Product;

import java.util.List;

/**
 * Created by Administrator on 02/11/2016.
 */
public interface CategoryDao extends GenericDao<Category, Integer> {

    List<Category> getCategoryByName(String name);

    List<Category> getAllCategory();

    List<Category> getAll(int firstResult, int maxResult);

    List<Category> checkDuplicateName(String name);

    void deleteCategory(int id);

    List<Product> getProductInCate(int categoryId);

    boolean addProductInCate(long[] listProductId);

    List<Category> getCategoryOfProduct(long productId);
}
