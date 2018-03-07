package com.android.quickenquiry.utils.util;

import com.android.quickenquiry.utils.util.pojoClasses.ContactDetail;

import java.util.Comparator;

/**
 * Created by user on 3/7/2018.
 */

public class ContactListComparator implements Comparator<ContactDetail> {
    @Override
    public int compare(ContactDetail o1, ContactDetail o2) {
        return o1.getmName().compareTo(o2.getmName());
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
