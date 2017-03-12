package com.fu.nlp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by manlm on 11/2/2016.
 */
public class EntityResponsePost {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("status")
    @Expose
    private Status status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
