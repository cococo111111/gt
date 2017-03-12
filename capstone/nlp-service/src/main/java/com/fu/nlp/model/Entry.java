package com.fu.nlp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by manlm on 11/2/2016.
 */
public class Entry {

    @SerializedName("value")
    @Expose
    private String value;

    @SerializedName("synonyms")
    @Expose
    private List<String> synonyms;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }
}
