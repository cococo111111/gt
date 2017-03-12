package com.fu.database.model;

import com.fu.database.entity.Product;

/**
 * Created by Administrator on 30/11/2016.
 */
public class RelatingModel {

    long id;

    String name;

    String imgUrl;

    long weight;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RelatingModel(long id, String name, String imgUrl, long weight) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.weight = weight;
    }
}
