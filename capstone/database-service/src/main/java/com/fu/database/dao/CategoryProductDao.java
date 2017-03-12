package com.fu.database.dao;

import com.fu.database.entity.CategoryProduct;
import com.fu.database.entity.CategoryProductKey;

import java.util.List;

/**
 * Created by Administrator on 18/11/2016.
 */
public interface CategoryProductDao extends GenericDao<CategoryProduct, CategoryProductKey>  {

    boolean deleteProductInCate(int categoryId, long productId);

    List<CategoryProduct> checkExistProductInCate(int categoryId, long productId);

    void deleteCategoryProduct(int categoryId);

    void deleteCategoryRelatingProduct(long productId);
}
