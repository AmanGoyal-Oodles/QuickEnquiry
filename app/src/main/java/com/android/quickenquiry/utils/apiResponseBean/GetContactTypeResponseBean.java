package com.android.quickenquiry.utils.apiResponseBean;

import com.android.quickenquiry.utils.util.pojoClasses.ContactType;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by user on 4/2/2018.
 */

public class GetContactTypeResponseBean {

    @SerializedName("response")
    private boolean response;
    @SerializedName("ctype")
    private ArrayList<ContactType> contactTypeList;

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public ArrayList<ContactType> getContactTypeList() {
        return contactTypeList;
    }

    public void setContactTypeList(ArrayList<ContactType> contactTypeList) {
        this.contactTypeList = contactTypeList;
    }
}
