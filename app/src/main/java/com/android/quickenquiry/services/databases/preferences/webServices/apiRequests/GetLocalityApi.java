package com.android.quickenquiry.services.databases.preferences.webServices.apiRequests;

import android.app.ProgressDialog;
import android.content.Context;
import com.android.quickenquiry.R;
import com.android.quickenquiry.interfaces.apiResponseListener.GetCategoryResponseListener;
import com.android.quickenquiry.interfaces.apiResponseListener.GetCityResponseListener;
import com.android.quickenquiry.interfaces.apiResponseListener.GetLocalityResponseListener;
import com.android.quickenquiry.services.databases.preferences.connectionClasses.UserConnection;
import com.android.quickenquiry.utils.apiResponseBean.GetCategoryAPIResponse;
import com.android.quickenquiry.utils.apiResponseBean.GetCityResponseBean;
import com.android.quickenquiry.utils.apiResponseBean.GetLocalityResponseBean;
import com.android.quickenquiry.utils.constants.ServerApi;
import com.android.quickenquiry.utils.retrofitAdapter.ConvertInputStream;
import com.android.quickenquiry.utils.retrofitAdapter.RetroFitAdapter;
import com.android.quickenquiry.utils.util.AppToast;
import com.android.quickenquiry.utils.util.InternetConnection;
import com.android.quickenquiry.utils.util.Logger;
import com.android.quickenquiry.utils.util.dialogs.DismissDialog;
import com.android.quickenquiry.utils.util.pojoClasses.CategoryType;
import com.android.quickenquiry.utils.util.pojoClasses.CityDetails;
import com.android.quickenquiry.utils.util.pojoClasses.LocalityDetails;
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

public class GetLocalityApi implements Callback<ResponseBody> {


    private Context mContext;
    private GetLocalityResponseListener mGetLocalityResponseListener;
    private static final String TAG=LoginApi.class.getName();
    private ProgressDialog mProgressDialog;

    public GetLocalityApi(Context context,GetLocalityResponseListener listener,ProgressDialog progressDialog) {
        mContext=context;
        mGetLocalityResponseListener=listener;
        mProgressDialog=progressDialog;
    }

    public void callGetLocalityApi(String cityId) {
        if(!InternetConnection.isInternetConnected(mContext)) {
            DismissDialog.dismissWithCheck(mProgressDialog);
            AppToast.showToast(mContext,mContext.getResources().getString(R.string.err_no_internet));
            return;
        }
        String key=ServerApi.API_KEY;
        UserConnection userConnection= RetroFitAdapter.createService(UserConnection.class, ServerApi.SERVER_URL);
        Call<ResponseBody> call=userConnection.getLocality(key,cityId);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        GetLocalityResponseBean getLocalityResponseBean=new GetLocalityResponseBean();
        ArrayList<LocalityDetails> localityList=new ArrayList<>();
        if(response.isSuccessful()) {
            InputStream stream=response.body().byteStream();
            String result=ConvertInputStream.getFormattedResponse(stream);
            Gson gson=new Gson();
            getLocalityResponseBean=gson.fromJson(result,GetLocalityResponseBean.class);
            //AppToast.showToast(mContext,"");
            localityList=getLocalityResponseBean.getLocalityList();
            afterSuccessfullResponse(localityList);
        } else {
            AppToast.showToast(mContext,"Category Not Loaded");
            afterSuccessfullResponse(localityList);
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Logger.LogError(TAG,t.getMessage());
        DismissDialog.dismissWithCheck(mProgressDialog);
        AppToast.showToast(mContext,"Network Error While loading Category");
    }

    private void afterSuccessfullResponse(ArrayList<LocalityDetails> localityList) {
        DismissDialog.dismissWithCheck(mProgressDialog);
        mGetLocalityResponseListener.getLocalityResponse(localityList);
    }

}