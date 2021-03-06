package com.android.quickenquiry.utils.util.pojoClasses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Cortana on 3/15/2018.
 */

public class CategoryType {

    @SerializedName("cateid")
    private String catId;
    @SerializedName("business_cate")
    private String catName;

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

}