package com.android.quickenquiry.utils.apiResponseBean;

import com.android.quickenquiry.utils.util.pojoClasses.CityDetails;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by user on 3/26/2018.
 */

public class GetCityResponseBean {

    @SerializedName("response")
    private boolean response;

    @SerializedName("getcity")
    private ArrayList<CityDetails> cityList=new ArrayList();

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public ArrayList<CityDetails> getCityList() {
        return cityList;
    }

    public void setCityList(ArrayList<CityDetails> cityList) {
        this.cityList = cityList;
    }
}
