package com.android.quickenquiry.utils.apiResponseBean;

import com.android.quickenquiry.utils.util.pojoClasses.ContactDetail;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by user on 3/27/2018.
 */

public class GetContactsResponseBean {

    @SerializedName("response")
    private boolean response;
    @SerializedName("ucontact")
    private ArrayList<ContactDetail> contactList;
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

    public ArrayList<ContactDetail> getContactList() {
        return contactList;
    }

    public void setContactList(ArrayList<ContactDetail> contactList) {
        this.contactList = contactList;
    }
}
