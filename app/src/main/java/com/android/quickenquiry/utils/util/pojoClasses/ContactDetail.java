package com.android.quickenquiry.utils.util.pojoClasses;

/**
 * Created by user on 3/7/2018.
 */

public class ContactDetail {

    private String mName;
    private String mPhone;
    private String mCompany;
    private String mEmail;
    private String mAddress;
    private String mDOB;
    private String mAnniversary;
    private String mClientType;

    public ContactDetail(String mName, String mPhone, String mCompany, String mEmail, String mAddress, String mDOB, String mAnniversary, String mClientType) {
        this.mName = mName;
        this.mPhone = mPhone;
        this.mCompany = mCompany;
        this.mEmail = mEmail;
        this.mAddress = mAddress;
        this.mDOB = mDOB;
        this.mAnniversary = mAnniversary;
        this.mClientType = mClientType;
    }

    public ContactDetail() {}

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

    public String getmCompany() {
        return mCompany;
    }

    public void setmCompany(String mCompany) {
        this.mCompany = mCompany;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmDOB() {
        return mDOB;
    }

    public void setmDOB(String mDOB) {
        this.mDOB = mDOB;
    }

    public String getmAnniversary() {
        return mAnniversary;
    }

    public void setmAnniversary(String mAnniversary) {
        this.mAnniversary = mAnniversary;
    }

    public String getmClientType() {
        return mClientType;
    }

    public void setmClientType(String mClientType) {
        this.mClientType = mClientType;
    }

    @Override
    public String toString() {
        return "";
        //return mName + " (" + mPhone + ")";
    }
}
