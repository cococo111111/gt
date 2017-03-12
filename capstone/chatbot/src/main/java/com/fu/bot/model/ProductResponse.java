package com.fu.bot.model;

import com.fu.database.model.ProductApi;
import com.fu.database.model.SaveData;

import java.util.List;

/**
 * Created by Administrator on 03/10/2016.
 */
public class ProductResponse {
    private List<SaveData> list;
    private int statusCode;

    public ProductResponse(List<SaveData> list, int statusCode) {
        this.list = list;
        this.statusCode = statusCode;
    }

    public List<SaveData> getList() {
        return list;
    }

    public void setList(List<SaveData> list) {
        this.list = list;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
