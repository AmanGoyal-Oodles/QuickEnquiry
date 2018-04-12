package com.android.quickenquiry.interfaces;

import com.android.quickenquiry.utils.util.pojoClasses.ContactType;

import java.util.ArrayList;

/**
 * Created by Cortana on 4/12/2018.
 */

public interface GetSelectedContactTypeListener {

    public void getSelectedContactType(ArrayList<ContactType> contactTypeList);


}