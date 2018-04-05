package com.android.quickenquiry.utils.util.pojoClasses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 4/2/2018.
 */

public class ContactType {

    @SerializedName("ctypeid")
    private String contactTypeId;
    @SerializedName("ctype_name")
    private String contactTypeName;

    public String getContactTypeId() {
        return contactTypeId;
    }

    public void setContactTypeId(String contactTypeId) {
        this.contactTypeId = contactTypeId;
    }

    public String getContactTypeName() {
        return contactTypeName;
    }

    public void setContactTypeName(String contactTypeName) {
        this.contactTypeName = contactTypeName;
    }
}
