package com.android.quickenquiry.interfaces;

import com.android.quickenquiry.utils.util.pojoClasses.ContactDetail;

import java.util.ArrayList;

/**
 * Created by Cortana on 4/12/2018.
 */

public interface GetSelectedContactListener {

    public void getSelectedContacct(ArrayList<ContactDetail> contactList);

}
