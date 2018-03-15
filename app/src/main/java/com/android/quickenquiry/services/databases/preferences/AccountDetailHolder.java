package com.android.quickenquiry.services.databases.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.quickenquiry.utils.apiResponseBean.UserResponseBean;
import com.android.quickenquiry.utils.util.pojoClasses.ContactDetail;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by user on 1/3/2018.
 */

public class AccountDetailHolder {

    private static SharedPreferences sharedPreferences;
    private static final String PREFERENCE_KEY = "user_detail_preference";
    private static final String KEY_IS_USER__LOGGED_IN="isUserLoggedIn";
    private static final String KEY_CONTACT_LIST="contactList";
    private static final String KEY_USER_DATEIL="userDetail";
    public static SharedPreferences.Editor editor;

    public AccountDetailHolder(Context context) {
        try {
            sharedPreferences = context.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ContactDetail> getContactList() {
        Gson gson=new Gson();
        String  jsonDetails=sharedPreferences.getString(KEY_CONTACT_LIST,null);
        Type type=new TypeToken<ArrayList<ContactDetail>>(){}.getType();
        ArrayList<ContactDetail> list=gson.fromJson(jsonDetails,type);
        if(list==null) {
            list=new ArrayList<>();
        }
        return list;
    }

    public void setContactList(ArrayList<ContactDetail> contactList) {
        Gson gson=new Gson();
        String jsonDetails=gson.toJson(contactList);
        sharedPreferences.edit().putString(KEY_CONTACT_LIST,jsonDetails).apply();
    }

    public void setUserLoggedIn(boolean status) {
        sharedPreferences.edit().putBoolean(KEY_IS_USER__LOGGED_IN,status).apply();
    }

    public boolean getIsUserLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_USER__LOGGED_IN,false);
    }

    public void setUserDetail(UserResponseBean userDetail) {
        Gson gson=new Gson();
        String json=gson.toJson(userDetail);
        sharedPreferences.edit().putString(KEY_USER_DATEIL,json).apply();
    }

    public UserResponseBean getUserDetail(){
        Gson gson=new Gson();
        String defaultJson=gson.toJson(new UserResponseBean());
        String json=sharedPreferences.getString(KEY_USER_DATEIL,defaultJson);
        UserResponseBean userDetail=gson.fromJson(json,UserResponseBean.class);
        if(userDetail==null) {
            userDetail=new UserResponseBean();
        }
        return userDetail;
    }

    public void clearAllSharedPreferences() {
        sharedPreferences.edit().remove(KEY_IS_USER__LOGGED_IN).apply();
    }

}