package com.android.quickenquiry.services.databases.preferences.webServices.apiRequests;

import android.app.ProgressDialog;
import android.content.Context;
import com.android.quickenquiry.R;
import com.android.quickenquiry.interfaces.apiResponseListener.GetCategoryResponseListener;
import com.android.quickenquiry.interfaces.apiResponseListener.GetContactTypeResponseListener;
import com.android.quickenquiry.services.databases.preferences.connectionClasses.UserConnection;
import com.android.quickenquiry.utils.apiResponseBean.GetCategoryAPIResponse;
import com.android.quickenquiry.utils.apiResponseBean.GetContactTypeResponseBean;
import com.android.quickenquiry.utils.constants.ServerApi;
import com.android.quickenquiry.utils.retrofitAdapter.ConvertInputStream;
import com.android.quickenquiry.utils.retrofitAdapter.RetroFitAdapter;
import com.android.quickenquiry.utils.util.AppToast;
import com.android.quickenquiry.utils.util.InternetConnection;
import com.android.quickenquiry.utils.util.Logger;
import com.android.quickenquiry.utils.util.dialogs.DismissDialog;
import com.android.quickenquiry.utils.util.pojoClasses.CategoryType;
import com.android.quickenquiry.utils.util.pojoClasses.ContactType;
import com.google.gson.Gson;
import java.io.InputStream;
import java.util.ArrayList;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Cortana on 1/11/2018.
 */

public class GetContactTypeApi implements Callback<ResponseBody> {


    private Context mContext;
    private GetContactTypeResponseListener mGetContactTypeResponseListener;
    private static final String TAG=LoginApi.class.getName();
    private ProgressDialog mProgressDialog;

    public GetContactTypeApi(Context context,GetContactTypeResponseListener listener,ProgressDialog progressDialog) {
        mContext=context;
        mGetContactTypeResponseListener=listener;
        mProgressDialog=progressDialog;
    }

    public void callGetContactTypeApi() {
        if(!InternetConnection.isInternetConnected(mContext)) {
            DismissDialog.dismissWithCheck(mProgressDialog);
            AppToast.showToast(mContext,mContext.getResources().getString(R.string.err_no_internet));
            return;
        }
        String key=ServerApi.API_KEY;
        UserConnection userConnection= RetroFitAdapter.createService(UserConnection.class, ServerApi.SERVER_URL);
        Call<ResponseBody> call=userConnection.getContactType(key);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        GetContactTypeResponseBean getContactTypeResponseBean=new GetContactTypeResponseBean();
        ArrayList<ContactType> contactTypeList=new ArrayList<>();
        if(response.isSuccessful()) {
            InputStream stream=response.body().byteStream();
            String result=ConvertInputStream.getFormattedResponse(stream);
            Gson gson=new Gson();
            getContactTypeResponseBean=gson.fromJson(result,GetContactTypeResponseBean.class);
            //AppToast.showToast(mContext,"");
            contactTypeList=getContactTypeResponseBean.getContactTypeList();
            afterSuccessfullResponse(getContactTypeResponseBean.isResponse(),contactTypeList);
        } else {
            AppToast.showToast(mContext,"Contact Type Not Loaded");
            afterSuccessfullResponse(getContactTypeResponseBean.isResponse(),contactTypeList);
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Logger.LogError(TAG,t.getMessage());
        DismissDialog.dismissWithCheck(mProgressDialog);
        AppToast.showToast(mContext,"Network Error");
    }

    private void afterSuccessfullResponse(boolean isReceived,ArrayList<ContactType> contactTypeList) {
        DismissDialog.dismissWithCheck(mProgressDialog);
        mGetContactTypeResponseListener.getContactTypeResponse(isReceived,contactTypeList);
    }

}