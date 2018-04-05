package com.android.quickenquiry.utils.util.pojoClasses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by user on 3/30/2018.
 */

public class ImportContactRequestBean {

    @SerializedName("key")
    private String key;
    @SerializedName("user_id")
    private String userid;
    @SerializedName("import_contact_arr")
    private ArrayList<ImportContactDetail> contactList;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public ArrayList<ImportContactDetail> getContactList() {
        return contactList;
    }

    public void setContactList(ArrayList<ImportContactDetail> contactList) {
        this.contactList = contactList;
    }
}
