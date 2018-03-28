package com.android.quickenquiry.interfaces.apiResponseListener;

import com.android.quickenquiry.utils.util.pojoClasses.LocalityDetails;

import java.util.ArrayList;

/**
 * Created by user on 3/26/2018.
 */

public interface GetLocalityResponseListener {

    public void getLocalityResponse(ArrayList<LocalityDetails> localityList);

}
