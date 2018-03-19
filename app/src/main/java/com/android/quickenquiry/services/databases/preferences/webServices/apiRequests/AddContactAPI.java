package com.android.quickenquiry.services.databases.preferences.webServices.apiRequests;

/**
 * Created by Cortana on 3/16/2018.
 */

import android.app.ProgressDialog;
import android.content.Context;
import com.android.quickenquiry.R;
import com.android.quickenquiry.interfaces.apiResponseListener.AddContactAPIResponseListener;
import com.android.quickenquiry.services.databases.preferences.connectionClasses.UserConnection;
import com.android.quickenquiry.utils.apiResponseBean.AddContactAPIResponse;
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

public class AddContactAPI implements Callback<ResponseBody> {


    private Context mContext;
    private AddContactAPIResponseListener mAddContactAPIResponseListener;
    private static final String TAG=LoginApi.class.getName();
    private ProgressDialog mProgressDialog;

    public AddContactAPI(Context context,AddContactAPIResponseListener listener,ProgressDialog progressDialog) {
        mContext=context;
        mAddContactAPIResponseListener=listener;
        mProgressDialog=progressDialog;
    }

    public void callAddContactApi(String userId,String contactType,String contactName,String contactMobile,String contactEmail,String contactAddress,String contactDOB,String contactAnniv) {
        if(!InternetConnection.isInternetConnected(mContext)) {
            DismissDialog.dismissWithCheck(mProgressDialog);
            AppToast.showToast(mContext,mContext.getResources().getString(R.string.err_no_internet));
            return;
        }
        String key= ServerApi.API_KEY;
        UserConnection userConnection= RetroFitAdapter.createService(UserConnection.class, ServerApi.SERVER_URL);
        Call<ResponseBody> call=userConnection.addContact(key,userId,contactType,contactName,contactMobile,contactEmail,contactAddress,contactDOB,contactAnniv);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        AddContactAPIResponse addContactAPIResponse=new AddContactAPIResponse();
        if(response.isSuccessful()) {
            InputStream stream=response.body().byteStream();
            String result= ConvertInputStream.getFormattedResponse(stream);
            Gson gson=new Gson();
            addContactAPIResponse=gson.fromJson(result,AddContactAPIResponse.class);
            AppToast.showToast(mContext,addContactAPIResponse.getMessage());
            DismissDialog.dismissWithCheck(mProgressDialog);
            afterSuccessfullResponse(addContactAPIResponse.isResponse());
        } else {
            AppToast.showToast(mContext,"Add Contact Failed.");
            afterSuccessfullResponse(false);
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Logger.LogError(TAG,t.getMessage());
        DismissDialog.dismissWithCheck(mProgressDialog);
        AppToast.showToast(mContext,"Network Error");
    }


    private void afterSuccessfullResponse(boolean isAdded) {
            mAddContactAPIResponseListener.getAddContactResponse(isAdded);
    }

}