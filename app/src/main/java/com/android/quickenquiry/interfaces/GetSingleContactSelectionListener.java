package com.android.quickenquiry.interfaces;

import com.android.quickenquiry.utils.util.pojoClasses.ContactDetail;

/**
 * Created by Cortana on 3/13/2018.
 */

public interface GetSingleContactSelectionListener {

    public void getSingleItemSelected(boolean isSelected, ContactDetail contactDetail);

}
