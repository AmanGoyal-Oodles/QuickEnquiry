package com.android.quickenquiry.utils.apiResponseBean;

import com.android.quickenquiry.utils.util.pojoClasses.LocalityDetails;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by user on 3/26/2018.
 */

public class GetLocalityResponseBean {

    @SerializedName("response")
    private boolean response;
    @SerializedName("locality")
    private ArrayList<LocalityDetails> localityList=new ArrayList();

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public ArrayList<LocalityDetails> getLocalityList() {
        return localityList;
    }

    public void setLocalityList(ArrayList<LocalityDetails> localityList) {
        this.localityList = localityList;
    }
}
