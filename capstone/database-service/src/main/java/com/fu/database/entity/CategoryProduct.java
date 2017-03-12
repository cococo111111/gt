package com.fu.database.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Administrator on 18/11/2016.
 */
@Entity
@Table(name = "category_product")
public class CategoryProduct {
    @EmbeddedId
    private CategoryProductKey categoryProductKey;

    public CategoryProduct() {
    }

    public int getCategoryId() {
        return categoryProductKey.getCategoryId();
    }

    public long getProductId() {
        return categoryProductKey.getProductId();
    }

    public CategoryProduct(CategoryProductKey categoryProductKey) {
        this.categoryProductKey = categoryProductKey;
    }

    public CategoryProductKey getCategoryProductKey() {
        return categoryProductKey;
    }

    public void setCategoryProductKey(CategoryProductKey categoryProductKey) {
        this.categoryProductKey = categoryProductKey;
    }

    public void setCategoryProductKey(int categoryId, long productId) {
        this.categoryProductKey = new CategoryProductKey(categoryId, productId);
    }
}
