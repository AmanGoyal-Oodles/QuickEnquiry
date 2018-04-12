package com.android.quickenquiry.interfaces.apiResponseListener;

import com.android.quickenquiry.utils.apiResponseBean.GetSMSApiResponse;
import com.android.quickenquiry.utils.apiResponseBean.SendSMSApiResponse;

/**
 * Created by Cortana on 4/11/2018.
 */

public interface GetSMSApiResponseListener {

    public void getSendSMSApiResponse(boolean isSent, GetSMSApiResponse getSMSApiResponse);

}
