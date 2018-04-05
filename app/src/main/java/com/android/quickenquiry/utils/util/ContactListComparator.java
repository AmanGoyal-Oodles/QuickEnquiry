package com.android.quickenquiry.utils.util;

import com.android.quickenquiry.utils.util.pojoClasses.ContactDetail;
import com.android.quickenquiry.utils.util.pojoClasses.ImportContactDetail;

import java.util.Comparator;

/**
 * Created by user on 3/7/2018.
 */

public class ContactListComparator implements Comparator<ImportContactDetail> {
    @Override
    public int compare(ImportContactDetail o1, ImportContactDetail o2) {
        return o1.getmName().compareTo(o2.getmName());
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
