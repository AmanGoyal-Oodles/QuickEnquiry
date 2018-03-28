package com.android.quickenquiry.interfaces.apiResponseListener;

import com.android.quickenquiry.utils.util.pojoClasses.CityDetails;

import java.util.ArrayList;

/**
 * Created by user on 3/26/2018.
 */

public interface GetCityResponseListener {

    public void getCityListResponse(ArrayList<CityDetails> cityList);

}
