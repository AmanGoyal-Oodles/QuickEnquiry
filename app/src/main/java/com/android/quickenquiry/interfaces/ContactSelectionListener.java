package com.android.quickenquiry.interfaces;

import com.android.quickenquiry.utils.util.pojoClasses.ContactDetail;

/**
 * Created by user on 3/8/2018.
 */

public interface ContactSelectionListener {

    public void contactSelected(ContactDetail contactDetail,int position,boolean isSelected);

}
