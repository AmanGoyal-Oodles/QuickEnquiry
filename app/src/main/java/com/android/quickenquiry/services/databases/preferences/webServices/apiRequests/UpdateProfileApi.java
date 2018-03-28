package com.android.quickenquiry.services.databases.preferences.webServices.apiRequests;

/**
 * Created by user on 3/26/2018.
 */

import android.app.ProgressDialog;
import android.content.Context;
import com.android.quickenquiry.R;
import com.android.quickenquiry.interfaces.apiResponseListener.UpdateProfileResponseListener;
import com.android.quickenquiry.services.databases.preferences.connectionClasses.UserConnection;
import com.android.quickenquiry.utils.apiResponseBean.UpdateProfileResponseBean;
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

public class UpdateProfileApi implements Callback<ResponseBody> {


    private Context mContext;
    private UpdateProfileResponseListener mUpdateProfileResponseListener;
    private static final String TAG=LoginApi.class.getName();
    private ProgressDialog mProgressDialog;

    public UpdateProfileApi(Context context, UpdateProfileResponseListener listener, ProgressDialog progressDialog) {
        mContext=context;
        mUpdateProfileResponseListener=listener;
        mProgressDialog=progressDialog;
    }

    public void callUpdateProfileApi(String userId,String userName,String userEmail,String secondaryContact,String cityId,String localityId,String addresss) {
        if(!InternetConnection.isInternetConnected(mContext)) {
            DismissDialog.dismissWithCheck(mProgressDialog);
            AppToast.showToast(mContext,mContext.getResources().getString(R.string.err_no_internet));
            return;
        }
        String key= ServerApi.API_KEY;
        UserConnection userConnection= RetroFitAdapter.createService(UserConnection.class, ServerApi.SERVER_URL);
        Call<ResponseBody> call=userConnection.updateProfile(key,userId,userName,userEmail,secondaryContact,cityId,localityId,addresss);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        UpdateProfileResponseBean updateProfileResponseBean=new UpdateProfileResponseBean();
        if(response.isSuccessful()) {
            InputStream stream=response.body().byteStream();
            String result= ConvertInputStream.getFormattedResponse(stream);
            Gson gson=new Gson();
            updateProfileResponseBean=gson.fromJson(result,UpdateProfileResponseBean.class);
            AppToast.showToast(mContext,updateProfileResponseBean.getMessage());
            DismissDialog.dismissWithCheck(mProgressDialog);
            afterSuccessfullResponse(updateProfileResponseBean.isResponse(),updateProfileResponseBean.getMessage());
        } else {
            AppToast.showToast(mContext,"User Login Failed.");
            afterSuccessfullResponse(false,updateProfileResponseBean.getMessage());
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Logger.LogError(TAG,t.getMessage());
        DismissDialog.dismissWithCheck(mProgressDialog);
        AppToast.showToast(mContext,"Network Error");
    }


    private void afterSuccessfullResponse(boolean isUpdated,String message) {
        mUpdateProfileResponseListener.getUpdateProfileResponse(isUpdated,message);
    }

}
