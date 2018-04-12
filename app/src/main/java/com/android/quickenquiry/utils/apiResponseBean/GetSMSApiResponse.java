package com.android.quickenquiry.utils.apiResponseBean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Cortana on 4/11/2018.
 */

public class GetSMSApiResponse {

    @SerializedName("sms_balance")
    private String sms_balance;
    @SerializedName("total_sms_sent")
    private String total_sms_sent;
    @SerializedName("crm_active_date")
    private String crm_active_date;
    @SerializedName("crm_expiry_date")
    private String crm_expiry_date;
    @SerializedName("package_name")
    private String package_name;
    @SerializedName("response")
    private boolean response;

    public String getSms_balance() {
        return sms_balance;
    }

    public void setSms_balance(String sms_balance) {
        this.sms_balance = sms_balance;
    }

    public String getTotal_sms_sent() {
        return total_sms_sent;
    }

    public void setTotal_sms_sent(String total_sms_sent) {
        this.total_sms_sent = total_sms_sent;
    }

    public String getCrm_active_date() {
        return crm_active_date;
    }

    public void setCrm_active_date(String crm_active_date) {
        this.crm_active_date = crm_active_date;
    }

    public String getCrm_expiry_date() {
        return crm_expiry_date;
    }

    public void setCrm_expiry_date(String crm_expiry_date) {
        this.crm_expiry_date = crm_expiry_date;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

}
