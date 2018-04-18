package com.android.quickenquiry.utils.apiResponseBean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Cortana on 4/18/2018.
 */

public class SendQueryAPIResponseBean {

    @SerializedName("response")
    private boolean response;
    @SerializedName("message")
    private String message;

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
