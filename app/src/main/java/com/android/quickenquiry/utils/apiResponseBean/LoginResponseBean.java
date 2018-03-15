package com.android.quickenquiry.utils.apiResponseBean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Cortana on 3/13/2018.
 */

public class LoginResponseBean {

    @SerializedName("response")
    private boolean response;
    @SerializedName("profile")
    private UserResponseBean profile;
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public UserResponseBean getProfile() {
        return profile;
    }

    public void setProfile(UserResponseBean profile) {
        this.profile = profile;
    }
}
