package com.android.quickenquiry.services.databases.preferences.webServices.apiRequests;

/**
 * Created by user on 3/26/2018.
 */

import android.app.ProgressDialog;
import android.content.Context;
import com.android.quickenquiry.R;
import com.android.quickenquiry.interfaces.apiResponseListener.ForgotPassMobileValidateResponseListener;
import com.android.quickenquiry.services.databases.preferences.connectionClasses.UserConnection;
import com.android.quickenquiry.utils.apiResponseBean.ForgotPassMobileValidateResponseBean;
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
 * Created by Cortana on 1/11/2018.
 */

public class ForgotPassMobileValidate implements Callback<ResponseBody> {


    private Context mContext;
    private ForgotPassMobileValidateResponseListener mFPMVListener;
    private static final String TAG=LoginApi.class.getName();
    private ProgressDialog mProgressDialog;

    public ForgotPassMobileValidate(Context context, ForgotPassMobileValidateResponseListener listener, ProgressDialog progressDialog) {
        mContext=context;
        mFPMVListener=listener;
        mProgressDialog=progressDialog;
    }

    public void callForgotPassMobileValidateApi(String phone) {
        if(!InternetConnection.isInternetConnected(mContext)) {
            DismissDialog.dismissWithCheck(mProgressDialog);
            AppToast.showToast(mContext,mContext.getResources().getString(R.string.err_no_internet));
            return;
        }
        String key= ServerApi.API_KEY;
        UserConnection userConnection= RetroFitAdapter.createService(UserConnection.class, ServerApi.SERVER_URL);
        Call<ResponseBody> call=userConnection.forgotPassMobileValidate(key,phone);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        ForgotPassMobileValidateResponseBean fPMVResponseBean=new ForgotPassMobileValidateResponseBean();
        if(response.isSuccessful()) {
            InputStream stream=response.body().byteStream();
            String result= ConvertInputStream.getFormattedResponse(stream);
            Gson gson=new Gson();
            fPMVResponseBean=gson.fromJson(result,ForgotPassMobileValidateResponseBean.class);
            AppToast.showToast(mContext,fPMVResponseBean.getMessage());
            afterSuccessfullResponse(fPMVResponseBean.isResponse(),fPMVResponseBean.getMessage());
        } else {
            AppToast.showToast(mContext,"User Login Failed.");
            afterSuccessfullResponse(false,fPMVResponseBean.getMessage());
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Logger.LogError(TAG,t.getMessage());
        DismissDialog.dismissWithCheck(mProgressDialog);
        AppToast.showToast(mContext,"Network Error");
    }

    private void afterSuccessfullResponse(boolean isSent,String message) {
        DismissDialog.dismissWithCheck(mProgressDialog);
        mFPMVListener.getForgotPassMobileValidateResponse(isSent,message);
    }

}
