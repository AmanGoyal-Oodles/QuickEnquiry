package com.android.quickenquiry.utils.apiResponseBean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 3/27/2018.
 */

public class UpdateProfileResponseBean {

    @SerializedName("response")
    private boolean response;
    @SerializedName("message")
    private String message;
    @SerializedName("profile")
    private UserResponseBean userResponseBean;

    public UserResponseBean getUserResponseBean() {
        return userResponseBean;
    }

    public void setUserResponseBean(UserResponseBean userResponseBean) {
        this.userResponseBean = userResponseBean;
    }

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
