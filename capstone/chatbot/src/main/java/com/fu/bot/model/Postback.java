package com.fu.bot.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by manlm on 9/3/2016.
 */
public class Postback {

    @SerializedName("payload")
    @Expose
    private String payload;

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
