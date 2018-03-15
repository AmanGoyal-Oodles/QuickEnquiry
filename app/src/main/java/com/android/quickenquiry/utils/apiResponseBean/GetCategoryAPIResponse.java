package com.android.quickenquiry.utils.apiResponseBean;

import com.android.quickenquiry.utils.util.pojoClasses.CategoryType;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Cortana on 3/15/2018.
 */

public class GetCategoryAPIResponse {

    @SerializedName("response")
    private boolean response;

    @SerializedName("ctype")
    private ArrayList<CategoryType> catList;

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public ArrayList<CategoryType> getCatList() {
        return catList;
    }

    public void setCatList(ArrayList<CategoryType> catList) {
        this.catList = catList;
    }
}
