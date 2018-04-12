package com.android.quickenquiry.services.databases.preferences.webServices.apiRequests;

import android.app.ProgressDialog;
import android.content.Context;
import com.android.quickenquiry.R;
import com.android.quickenquiry.interfaces.apiResponseListener.GetSMSApiResponseListener;
import com.android.quickenquiry.services.databases.preferences.connectionClasses.UserConnection;
import com.android.quickenquiry.utils.apiResponseBean.GetSMSApiResponse;
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

public class GetSMSApi implements Callback<ResponseBody> {


    private Context mContext;
    private GetSMSApiResponseListener mGetSMSApiResponseListener;
    private static final String TAG=LoginApi.class.getName();
    private ProgressDialog mProgressDialog;

    public GetSMSApi(Context context, GetSMSApiResponseListener listener, ProgressDialog progressDialog) {
        mContext=context;
        mGetSMSApiResponseListener=listener;
        mProgressDialog=progressDialog;
    }

    public void callGetSMSApi(String userId) {
        if(!InternetConnection.isInternetConnected(mContext)) {
            DismissDialog.dismissWithCheck(mProgressDialog);
            AppToast.showToast(mContext,mContext.getResources().getString(R.string.err_no_internet));
            return;
        }
        String key= ServerApi.API_KEY;
        UserConnection userConnection= RetroFitAdapter.createService(UserConnection.class, ServerApi.SERVER_URL);
        Call<ResponseBody> call = userConnection.getSendSMS(key, userId);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        GetSMSApiResponse sendSMSApiResponse=new GetSMSApiResponse();
        if(response.isSuccessful()) {
            InputStream stream=response.body().byteStream();
            String result= ConvertInputStream.getFormattedResponse(stream);
            Gson gson=new Gson();
            sendSMSApiResponse=gson.fromJson(result,GetSMSApiResponse.class);
            afterSuccessfullResponse(sendSMSApiResponse.isResponse(),sendSMSApiResponse);
        } else {
            AppToast.showToast(mContext,"Send SMS Data NOt Loaded.");
            afterSuccessfullResponse(sendSMSApiResponse.isResponse(),sendSMSApiResponse);
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Logger.LogError(TAG,t.getMessage());
        DismissDialog.dismissWithCheck(mProgressDialog);
        AppToast.showToast(mContext,"Network Error");
    }


    private void afterSuccessfullResponse(boolean isSent,GetSMSApiResponse getSMSApiResponse) {
        DismissDialog.dismissWithCheck(mProgressDialog);
        mGetSMSApiResponseListener.getSendSMSApiResponse(isSent, getSMSApiResponse);
    }

}
