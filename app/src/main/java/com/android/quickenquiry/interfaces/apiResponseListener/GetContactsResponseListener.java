package com.android.quickenquiry.interfaces.apiResponseListener;

import com.android.quickenquiry.utils.util.pojoClasses.ContactDetail;

import java.util.ArrayList;

/**
 * Created by user on 3/27/2018.
 */

public interface GetContactsResponseListener {

    public void getContectsResponse(boolean isReceived, ArrayList<ContactDetail> contactList);

}
