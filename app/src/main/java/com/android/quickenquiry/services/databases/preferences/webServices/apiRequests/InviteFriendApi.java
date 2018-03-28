package com.android.quickenquiry.services.databases.preferences.webServices.apiRequests;

/**
 * Created by user on 3/26/2018.
 */

import android.app.ProgressDialog;
import android.content.Context;
import com.android.quickenquiry.R;
import com.android.quickenquiry.interfaces.apiResponseListener.InviteFriendResponseListener;
import com.android.quickenquiry.services.databases.preferences.connectionClasses.UserConnection;
import com.android.quickenquiry.utils.apiResponseBean.InviteFriendResponseBean;
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

public class InviteFriendApi implements Callback<ResponseBody> {


    private Context mContext;
    private InviteFriendResponseListener mInviteFriendResponseListener;
    private static final String TAG=LoginApi.class.getName();
    private ProgressDialog mProgressDialog;

    public InviteFriendApi(Context context, InviteFriendResponseListener listener, ProgressDialog progressDialog) {
        mContext=context;
        mInviteFriendResponseListener=listener;
        mProgressDialog=progressDialog;
    }

    public void callInviteFriendApi(String userId,String contacts) {
        if(!InternetConnection.isInternetConnected(mContext)) {
            DismissDialog.dismissWithCheck(mProgressDialog);
            AppToast.showToast(mContext,mContext.getResources().getString(R.string.err_no_internet));
            return;
        }
        String key= ServerApi.API_KEY;
        UserConnection userConnection= RetroFitAdapter.createService(UserConnection.class, ServerApi.SERVER_URL);
        Call<ResponseBody> call=userConnection.inviteToFriend(key,userId,contacts);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        InviteFriendResponseBean inviteFriendResponseBean=new InviteFriendResponseBean();
        if(response.isSuccessful()) {
            InputStream stream=response.body().byteStream();
            String result= ConvertInputStream.getFormattedResponse(stream);
            Gson gson=new Gson();
            inviteFriendResponseBean=gson.fromJson(result,InviteFriendResponseBean.class);
            AppToast.showToast(mContext,inviteFriendResponseBean.getMessage());
            afterSuccessfullResponse(inviteFriendResponseBean.isResponse(),inviteFriendResponseBean.getMessage());
        } else {
            AppToast.showToast(mContext,"User Login Failed.");
            afterSuccessfullResponse(inviteFriendResponseBean.isResponse(),inviteFriendResponseBean.getMessage());
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Logger.LogError(TAG,t.getMessage());
        DismissDialog.dismissWithCheck(mProgressDialog);
        AppToast.showToast(mContext,"Network Error");
    }


    private void afterSuccessfullResponse(boolean isInvited,String message) {
        DismissDialog.dismissWithCheck(mProgressDialog);
        mInviteFriendResponseListener.getInviteFriendResponse(isInvited, message);
    }

}
