package com.android.quickenquiry.services.databases.preferences.webServices.apiRequests;

import android.app.ProgressDialog;
import android.content.Context;
import com.android.quickenquiry.R;
import com.android.quickenquiry.interfaces.apiResponseListener.LoginResponseListener;
import com.android.quickenquiry.services.databases.preferences.connectionClasses.UserConnection;
import com.android.quickenquiry.utils.apiResponseBean.LoginResponseBean;
import com.android.quickenquiry.utils.apiResponseBean.UserResponseBean;
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

public class ValidateOTPAPI implements Callback<ResponseBody> {


    private Context mContext;
    private LoginResponseListener mLoginResponseListener;
    private static final String TAG=ValidateOTPAPI.class.getName();
    private ProgressDialog mProgressDialog;

    public ValidateOTPAPI(Context context,LoginResponseListener loginResponseListener,ProgressDialog progressDialog) {
        mContext=context;
        mLoginResponseListener=loginResponseListener;
        mProgressDialog=progressDialog;
    }

    public void callValidateOTPApi(String mobile, String otp) {
        if(!InternetConnection.isInternetConnected(mContext)) {
            DismissDialog.dismissWithCheck(mProgressDialog);
            AppToast.showToast(mContext,mContext.getResources().getString(R.string.err_no_internet));
            return;
        }
        String key=ServerApi.API_KEY;
        UserConnection userConnection= RetroFitAdapter.createService(UserConnection.class, ServerApi.SERVER_URL);
        Call<ResponseBody> call=userConnection.validateOTP(key,mobile,otp);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        UserResponseBean userDetailBean=new UserResponseBean();
        LoginResponseBean loginResponseBean=new LoginResponseBean();
        if(response.isSuccessful()) {
            InputStream stream=response.body().byteStream();
            String result=ConvertInputStream.getFormattedResponse(stream);
            Gson gson=new Gson();
            loginResponseBean=gson.fromJson(result,LoginResponseBean.class);
            AppToast.showToast(mContext,loginResponseBean.getMessage());
            DismissDialog.dismissWithCheck(mProgressDialog);
            afterSuccessfullResponse(loginResponseBean);
        } else {
            AppToast.showToast(mContext,"OTP Validation Failed.");
            afterSuccessfullResponse(loginResponseBean);
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Logger.LogError(TAG,t.getMessage());
        DismissDialog.dismissWithCheck(mProgressDialog);
        AppToast.showToast(mContext,"Network Error");
    }


    private void afterSuccessfullResponse(LoginResponseBean loginResponseBean) {
        mLoginResponseListener.getLoginResponse(loginResponseBean.isResponse(),loginResponseBean.getProfile());
    }

}