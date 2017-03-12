package com.fu.bom.service;

import com.fu.database.entity.Category;
import com.fu.database.entity.Product;

import java.util.List;

/**
 * Created by Administrator on 08/11/2016.
 */
public interface CategoryService {

    boolean addCategory(String name);

    boolean updateCategory(int id, String name);

    boolean deleteCategory(int id);

    List<Category> getAll();

    List<Product> getProductInCate(int categoryId);

    boolean addProductInCate(int categoryId, long[] listProductId);

    boolean deleteProductInCate(int categoryId, long productId);

    List<Category> getCategoryOfProduct(long productId);
}
