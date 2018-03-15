package com.android.quickenquiry.interfaces.apiResponseListener;

import com.android.quickenquiry.utils.apiResponseBean.UserResponseBean;

/**
 * Created by Cortana on 3/13/2018.
 */

public interface LoginResponseListener {

    public void getLoginResponse(boolean isLogin, UserResponseBean userResponseBean);

}
