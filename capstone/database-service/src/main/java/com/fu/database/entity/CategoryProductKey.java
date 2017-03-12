package com.fu.database.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Administrator on 18/11/2016.
 */
@Embeddable
public class CategoryProductKey implements Serializable {

    @Column(name = "categoryId")
    @NotNull
    private int categoryId;

    @Column(name = "productId")
    @NotNull
    private long productId;

    public CategoryProductKey() {
    }

    public CategoryProductKey(int categoryId, long productId) {
        this.categoryId = categoryId;
        this.productId = productId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}
