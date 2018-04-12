package com.android.quickenquiry.services.databases.preferences.webServices.apiRequests;

import android.app.ProgressDialog;
import android.content.Context;
import com.android.quickenquiry.R;
import com.android.quickenquiry.interfaces.apiResponseListener.SendSMSApiResponseListener;
import com.android.quickenquiry.services.databases.preferences.connectionClasses.UserConnection;
import com.android.quickenquiry.utils.apiResponseBean.SendSMSApiResponse;
import com.android.quickenquiry.utils.constants.ServerApi;
import com.android.quickenquiry.utils.retrofitAdapter.ConvertInputStream;
import com.android.quickenquiry.utils.retrofitAdapter.RetroFitAdapter;
import com.android.quickenquiry.utils.util.AppToast;
import com.android.quickenquiry.utils.util.InternetConnection;
import com.android.quickenquiry.utils.util.Logger;
import com.android.quickenquiry.utils.util.dialogs.DismissDialog;
import com.google.gson.Gson;
import java.io.InputStream;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Cortana on 4/11/2018.
 */

public class SendSMSApi implements Callback<ResponseBody> {


    private Context mContext;
    private SendSMSApiResponseListener mSendSMSApiResponseListener;
    private static final String TAG=LoginApi.class.getName();
    private ProgressDialog mProgressDialog;

    public SendSMSApi(Context context, SendSMSApiResponseListener listener, ProgressDialog progressDialog) {
        mContext=context;
        mSendSMSApiResponseListener=listener;
        mProgressDialog=progressDialog;
    }

    public void callInviteFriendApi(String userId,String contacts,String contactType,String message) {
        if(!InternetConnection.isInternetConnected(mContext)) {
            DismissDialog.dismissWithCheck(mProgressDialog);
            AppToast.showToast(mContext,mContext.getResources().getString(R.string.err_no_internet));
            return;
        }
        String key= ServerApi.API_KEY;
        UserConnection userConnection= RetroFitAdapter.createService(UserConnection.class, ServerApi.SERVER_URL);
        if(contactType.equalsIgnoreCase("contactType")) {
            Call<ResponseBody> call = userConnection.sendSMSMultiple(key, userId, contacts,message);
            call.enqueue(this);
        } else {
            Call<ResponseBody> call = userConnection.sendSMSSingle(key, userId, contacts,message);
            call.enqueue(this);
        }
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        SendSMSApiResponse sendSMSApiResponse=new SendSMSApiResponse();
        if(response.isSuccessful()) {
            InputStream stream=response.body().byteStream();
            String result= ConvertInputStream.getFormattedResponse(stream);
            Gson gson=new Gson();
            sendSMSApiResponse=gson.fromJson(result,SendSMSApiResponse.class);
            AppToast.showToast(mContext,sendSMSApiResponse.getMessage());
            afterSuccessfullResponse(sendSMSApiResponse.isResponse(),sendSMSApiResponse);
        } else {
            AppToast.showToast(mContext,"Send SMS Failed.");
            afterSuccessfullResponse(sendSMSApiResponse.isResponse(),sendSMSApiResponse);
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Logger.LogError(TAG,t.getMessage());
        DismissDialog.dismissWithCheck(mProgressDialog);
        AppToast.showToast(mContext,"Network Error");
    }


    private void afterSuccessfullResponse(boolean isSent,SendSMSApiResponse sendSMSApiResponse) {
        DismissDialog.dismissWithCheck(mProgressDialog);
        mSendSMSApiResponseListener.getSendSMSApiResponse(isSent, sendSMSApiResponse);
    }

}