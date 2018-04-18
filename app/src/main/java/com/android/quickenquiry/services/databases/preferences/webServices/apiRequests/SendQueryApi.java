package com.android.quickenquiry.services.databases.preferences.webServices.apiRequests;

import android.app.ProgressDialog;
import android.content.Context;
import com.android.quickenquiry.R;
import com.android.quickenquiry.interfaces.apiResponseListener.SendQueryResponseListener;
import com.android.quickenquiry.interfaces.apiResponseListener.SendSMSApiResponseListener;
import com.android.quickenquiry.services.databases.preferences.connectionClasses.UserConnection;
import com.android.quickenquiry.utils.apiResponseBean.SendQueryAPIResponseBean;
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

public class SendQueryApi implements Callback<ResponseBody> {


    private Context mContext;
    private SendQueryResponseListener mSendQueryResponseListener;
    private static final String TAG=LoginApi.class.getName();
    private ProgressDialog mProgressDialog;

    public SendQueryApi(Context context, SendQueryResponseListener listener, ProgressDialog progressDialog) {
        mContext=context;
        mSendQueryResponseListener=listener;
        mProgressDialog=progressDialog;
    }

    public void callSendQueryApi(String userId,String ipAddress,String desc) {
        if(!InternetConnection.isInternetConnected(mContext)) {
            DismissDialog.dismissWithCheck(mProgressDialog);
            AppToast.showToast(mContext,mContext.getResources().getString(R.string.err_no_internet));
            return;
        }
        String key= ServerApi.API_KEY;
        UserConnection userConnection= RetroFitAdapter.createService(UserConnection.class, ServerApi.SERVER_URL);
            Call<ResponseBody> call = userConnection.sendQuery(key, userId, ipAddress,desc);
            call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        SendQueryAPIResponseBean sendQueryAPIResponseBean=new SendQueryAPIResponseBean();
        if(response.isSuccessful()) {
            InputStream stream=response.body().byteStream();
            String result= ConvertInputStream.getFormattedResponse(stream);
            Gson gson=new Gson();
            sendQueryAPIResponseBean=gson.fromJson(result,SendQueryAPIResponseBean.class);
            AppToast.showToast(mContext,sendQueryAPIResponseBean.getMessage());
            afterSuccessfullResponse(sendQueryAPIResponseBean.isResponse(),sendQueryAPIResponseBean.getMessage());
        } else {
            AppToast.showToast(mContext,"Send Query Failed.");
            afterSuccessfullResponse(sendQueryAPIResponseBean.isResponse(),sendQueryAPIResponseBean.getMessage());
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Logger.LogError(TAG,t.getMessage());
        DismissDialog.dismissWithCheck(mProgressDialog);
        AppToast.showToast(mContext,"Network Error");
    }

    private void afterSuccessfullResponse(boolean isSubmit,String message) {
        DismissDialog.dismissWithCheck(mProgressDialog);
        mSendQueryResponseListener.getSendQueryResponse(isSubmit,message);
    }

}