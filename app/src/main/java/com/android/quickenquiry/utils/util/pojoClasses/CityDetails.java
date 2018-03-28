package com.android.quickenquiry.utils.util.pojoClasses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 3/26/2018.
 */

public class CityDetails {


    @SerializedName("cityid")
    private String cityId;
    @SerializedName("city_type")
    private String cityType;
    @SerializedName("city_lat")
    private String cityLat;
    @SerializedName("city_lon")
    private String cityLon;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityType() {
        return cityType;
    }

    public void setCityType(String cityType) {
        this.cityType = cityType;
    }

    public String getCityLat() {
        return cityLat;
    }

    public void setCityLat(String cityLat) {
        this.cityLat = cityLat;
    }

    public String getCityLon() {
        return cityLon;
    }

    public void setCityLon(String cityLon) {
        this.cityLon = cityLon;
    }
}
