package com.android.quickenquiry.interfaces.apiResponseListener;

import com.android.quickenquiry.utils.apiResponseBean.UserResponseBean;

/**
 * Created by user on 3/27/2018.
 */

public interface UpdateProfileResponseListener {

    public void getUpdateProfileResponse(boolean isUpdated, String message, UserResponseBean userResponseBean);

}
