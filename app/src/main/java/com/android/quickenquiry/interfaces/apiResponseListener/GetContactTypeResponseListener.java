package com.android.quickenquiry.interfaces.apiResponseListener;

import com.android.quickenquiry.utils.util.pojoClasses.ContactType;

import java.util.ArrayList;

/**
 * Created by user on 4/2/2018.
 */

public interface GetContactTypeResponseListener {

    public void getContactTypeResponse(boolean isReceived, ArrayList<ContactType> contactTypeList);

}
