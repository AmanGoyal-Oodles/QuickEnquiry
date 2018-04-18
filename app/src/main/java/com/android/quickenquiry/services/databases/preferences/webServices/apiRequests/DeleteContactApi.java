package com.android.quickenquiry.services.databases.preferences.webServices.apiRequests;

/**
 * Created by user on 3/26/2018.
 */

import android.app.ProgressDialog;
import android.content.Context;
import com.android.quickenquiry.R;
import com.android.quickenquiry.interfaces.apiResponseListener.ChangePasswordResponseListener;
import com.android.quickenquiry.interfaces.apiResponseListener.DeleteContactResponseListener;
import com.android.quickenquiry.interfaces.apiResponseListener.ForgotPassResetPassResponseListener;
import com.android.quickenquiry.services.databases.preferences.connectionClasses.UserConnection;
import com.android.quickenquiry.utils.apiResponseBean.ChangePasswordRespoonseBean;
import com.android.quickenquiry.utils.apiResponseBean.DeleteContactResponseBean;
import com.android.quickenquiry.utils.apiResponseBean.ForgotPassResetPassResponseBean;
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

public class DeleteContactApi implements Callback<ResponseBody> {


    private Context mContext;
    private DeleteContactResponseListener mDeleteContactResponseListener;
    private static final String TAG=LoginApi.class.getName();
    private ProgressDialog mProgressDialog;
    private String mContactId="";

    public DeleteContactApi(Context context, DeleteContactResponseListener listener, ProgressDialog progressDialog) {
        mContext=context;
        mDeleteContactResponseListener=listener;
        mProgressDialog=progressDialog;
    }

    public void callDeleteContactApi(String userId,String contact_id) {
        mContactId=contact_id;
        if(!InternetConnection.isInternetConnected(mContext)) {
            DismissDialog.dismissWithCheck(mProgressDialog);
            AppToast.showToast(mContext,mContext.getResources().getString(R.string.err_no_internet));
            return;
        }
        String key= ServerApi.API_KEY;
        UserConnection userConnection= RetroFitAdapter.createService(UserConnection.class, ServerApi.SERVER_URL);
        Call<ResponseBody> call=userConnection.deleteContact(key,userId,contact_id);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        DeleteContactResponseBean deleteContactResponseBean=new DeleteContactResponseBean();
        if(response.isSuccessful()) {
            InputStream stream=response.body().byteStream();
            String result= ConvertInputStream.getFormattedResponse(stream);
            Gson gson=new Gson();
            deleteContactResponseBean=gson.fromJson(result,DeleteContactResponseBean.class);
            AppToast.showToast(mContext,deleteContactResponseBean.getMessage());
            afterSuccessfullResponse(deleteContactResponseBean.isResponse(),deleteContactResponseBean.getMessage());
        } else {
            AppToast.showToast(mContext,"User Login Failed.");
            afterSuccessfullResponse(false,deleteContactResponseBean.getMessage());
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Logger.LogError(TAG,t.getMessage());
        DismissDialog.dismissWithCheck(mProgressDialog);
        AppToast.showToast(mContext,"Network Error");
    }


    private void afterSuccessfullResponse(boolean isDelete,String message) {
        DismissDialog.dismissWithCheck(mProgressDialog);
        mDeleteContactResponseListener.getDeleteContactResponse(isDelete,message,mContactId);
    }

}
