package com.android.quickenquiry.interfaces.apiResponseListener;

import com.android.quickenquiry.utils.util.pojoClasses.CategoryType;

import java.util.ArrayList;

/**
 * Created by Cortana on 3/15/2018.
 */

public interface GetCategoryResponseListener {

    public void getCatResponse(ArrayList<CategoryType> catList);

}
