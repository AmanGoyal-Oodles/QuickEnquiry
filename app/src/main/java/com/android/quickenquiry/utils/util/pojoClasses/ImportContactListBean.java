package com.android.quickenquiry.utils.util.pojoClasses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by user on 3/28/2018.
 */

public class ImportContactListBean {

    @SerializedName("import_contact_arr")
    private ArrayList<ContactDetail> contactList;

    public ArrayList<ContactDetail> getContactList() {
        return contactList;
    }

    public void setContactList(ArrayList<ContactDetail> contactList) {
        this.contactList = contactList;
    }
}
