package com.android.quickenquiry.services.databases.preferences.connectionClasses;

import com.android.quickenquiry.utils.constants.ServerApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by user on 3/9/2018.
 */

public interface UserConnection {

    @POST(ServerApi.LOGIN)
    Call<ResponseBody> userLoginIn(@Query("key")String key, @Query("mobile")String mobile, @Query("password")String password);

    @POST(ServerApi.REGISTER)
    Call<ResponseBody> userRegister(@Query("key")String key, @Query("user_type")String userType,@Query("company_name")String companyNamee,@Query("user_name")String userName,@Query("user_mobile")String userMobile,@Query("user_email")String userEmail, @Query("password")String password, @Query("busi_cat")String busCat);

    @GET(ServerApi.VALIDATE_OTP)
    Call<ResponseBody> validateOTP(@Query("key")String key, @Query("user_mobile")String mobile, @Query("otp")String otp);

    @POST(ServerApi.GET_CATEGORY)
    Call<ResponseBody> getCategory(@Query("key")String key);

    @POST(ServerApi.ADD_CONTACT)
    Call<ResponseBody> addContact(@Query("key")String key, @Query("user_id")String userId,@Query("contact_type")String contactType,@Query("contact_name")String contactName,@Query("contact_mob")String contactMobile,@Query("contact_email")String contactEmail, @Query("contact_add")String contactAddress, @Query("contact_dob")String contactDOB,@Query("contact_anniversary")String contactAnniv);


}