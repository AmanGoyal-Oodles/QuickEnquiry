package com.android.quickenquiry.utils.util.pojoClasses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 3/26/2018.
 */

public class LocalityDetails {


    @SerializedName("localityid")
    private String localityId;
    @SerializedName("locality_name")
    private String localityName;
    @SerializedName("locality_lat")
    private String localityLat;
    @SerializedName("locality_lon")
    private String localityLon;

    public String getLocalityId() {
        return localityId;
    }

    public void setLocalityId(String localityId) {
        this.localityId = localityId;
    }

    public String getLocalityName() {
        return localityName;
    }

    public void setLocalityName(String localityName) {
        this.localityName = localityName;
    }

    public String getLocalityLat() {
        return localityLat;
    }

    public void setLocalityLat(String localityLat) {
        this.localityLat = localityLat;
    }

    public String getLocalityLon() {
        return localityLon;
    }

    public void setLocalityLon(String localityLon) {
        this.localityLon = localityLon;
    }
}
