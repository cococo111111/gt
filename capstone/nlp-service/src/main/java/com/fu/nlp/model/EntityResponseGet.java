package com.fu.nlp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by manlm on 11/2/2016.
 */
public class EntityResponseGet {

    @SerializedName("entities")
    @Expose
    private List<Entity> entities;

    @SerializedName("status")
    @Expose
    private Status status;

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
