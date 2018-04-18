package com.android.quickenquiry.services.databases.preferences.webServices.apiRequests;

import android.app.ProgressDialog;
import android.content.Context;
import com.android.quickenquiry.R;
import com.android.quickenquiry.interfaces.apiResponseListener.LoginResponseListener;
import com.android.quickenquiry.interfaces.apiResponseListener.RegisterResponseListener;
import com.android.quickenquiry.services.databases.preferences.connectionClasses.UserConnection;
import com.android.quickenquiry.utils.apiResponseBean.LoginResponseBean;
import com.android.quickenquiry.utils.apiResponseBean.RegisterResponseBean;
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

public class RegisterApi implements Callback<ResponseBody> {


    private Context mContext;
    private RegisterResponseListener mRegisterResponseListener;
    private static final String TAG=LoginApi.class.getName();
    private ProgressDialog mProgressDialog;

    public RegisterApi(Context context,RegisterResponseListener listener,ProgressDialog progressDialog) {
        mContext=context;
        mRegisterResponseListener=listener;
        mProgressDialog=progressDialog;
    }

    public void callRegisterApi(int userType,String companyName,String userName,String userMobile,String userEmail,String password,String busCat) {
        if(!InternetConnection.isInternetConnected(mContext)) {
            DismissDialog.dismissWithCheck(mProgressDialog);
            AppToast.showToast(mContext,mContext.getResources().getString(R.string.err_no_internet));
            return;
        }
        String key=ServerApi.API_KEY;
        UserConnection userConnection= RetroFitAdapter.createService(UserConnection.class, ServerApi.SERVER_URL);
        Call<ResponseBody> call=userConnection.userRegister(key,userType,companyName,userName,userMobile,userEmail,password,busCat);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        RegisterResponseBean registerResponseBean=new RegisterResponseBean();
        if(response.isSuccessful()) {
            InputStream stream=response.body().byteStream();
            String result=ConvertInputStream.getFormattedResponse(stream);
            Gson gson=new Gson();
            registerResponseBean=gson.fromJson(result,RegisterResponseBean.class);
            AppToast.showToast(mContext,registerResponseBean.getMessage());
            DismissDialog.dismissWithCheck(mProgressDialog);
            afterSuccessfullResponse(registerResponseBean);
        } else {
            AppToast.showToast(mContext,"User Register Failed.");
            afterSuccessfullResponse(registerResponseBean);
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Logger.LogError(TAG,t.getMessage());
        DismissDialog.dismissWithCheck(mProgressDialog);
        AppToast.showToast(mContext,"Network Error");
    }


    private void afterSuccessfullResponse(RegisterResponseBean registerResponseBean) {
        if(registerResponseBean.isResponse()&&registerResponseBean.getMessage()!=null) {
            mRegisterResponseListener.getRegisterResponse(true);
        }
        ///mLoginResponseListener.getLoginResponse(loginResponseBean.isResponse(),loginResponseBean.getProfile());
    }

}