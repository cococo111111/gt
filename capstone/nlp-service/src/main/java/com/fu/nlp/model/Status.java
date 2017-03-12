package com.fu.nlp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by manlm on 11/2/2016.
 */
public class Status {

    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("errorType")
    @Expose
    private String errorType;

    @SerializedName("errorId")
    @Expose
    private String errorId;

    @SerializedName("errorDetails")
    @Expose
    private String errorDetails;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorId() {
        return errorId;
    }

    public void setErrorId(String errorId) {
        this.errorId = errorId;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }
}
