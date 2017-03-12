package com.fu.nlp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by manlm on 11/2/2016.
 */
public class EntityRequestPost {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("entries")
    @Expose
    private List<Entry> entries;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }
}
