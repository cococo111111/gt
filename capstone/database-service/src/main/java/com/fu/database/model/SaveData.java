package com.fu.database.model;

import java.io.Serializable;

/**
 * Created by Administrator on 20/10/2016.
 */

public class SaveData implements Serializable{

    long productId;
    long timeHandle;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getTimeHandle() {
        return timeHandle;
    }

    public void setTimeHandle(long timeHandle) {
        this.timeHandle = timeHandle;
    }

    public SaveData(long productId, long timeHandle) {
        this.productId = productId;
        this.timeHandle = timeHandle;
    }

}
