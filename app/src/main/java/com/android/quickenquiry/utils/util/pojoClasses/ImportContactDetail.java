package com.android.quickenquiry.utils.util.pojoClasses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 4/5/2018.
 */

public class ImportContactDetail {

    @SerializedName("contact_name")
    private String mName;
    @SerializedName("contact_mob")
    private String mPhone;

    public ImportContactDetail(String mName, String mPhone) {
        this.mName = mName;
        this.mPhone = mPhone;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }
}