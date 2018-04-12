package com.android.quickenquiry.services.databases.preferences.connectionClasses;

import com.android.quickenquiry.utils.constants.ServerApi;
import com.android.quickenquiry.utils.util.pojoClasses.ImportContactRequestBean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by user on 3/9/2018.
 */

public interface UserConnection {

    @POST(ServerApi.LOGIN)
    Call<ResponseBody> userLoginIn(@Query("key") String key, @Query("mobile") String mobile, @Query("password") String password);

    @POST(ServerApi.REGISTER)
    Call<ResponseBody> userRegister(@Query("key") String key, @Query("user_type") int userType, @Query("company_name") String companyNamee, @Query("user_name") String userName, @Query("user_mobile") String userMobile, @Query("user_email") String userEmail, @Query("password") String password, @Query("busi_cat") String busCat);

    @POST(ServerApi.VALIDATE_OTP)
    Call<ResponseBody> validateOTP(@Query("key") String key, @Query("user_mobile") String mobile, @Query("otp") String otp);

    @GET(ServerApi.GET_BUSINESS_CATEGORY)
    Call<ResponseBody> getCategory(@Query("key") String key);

    @GET(ServerApi.GET_CONTACT_TYPE)
    Call<ResponseBody> getContactType(@Query("key") String key);

    @GET(ServerApi.GET_CITY)
    Call<ResponseBody> getCity(@Query("key") String key);

    @POST(ServerApi.GET_LOCALITY)
    Call<ResponseBody> getLocality(@Query("key") String key, @Query("city_id") String cityId);

    @POST(ServerApi.ADD_CONTACT)
    Call<ResponseBody> addContact(@Query("key") String key, @Query("user_id") String userId, @Query("contact_type") String contactType,  @Query("contact_rel") String contactRelation,@Query("contact_name") String contactName, @Query("contact_mob") String contactMobile, @Query("contact_email") String contactEmail, @Query("contact_add") String contactAddress, @Query("contact_dob") String contactDOB, @Query("contact_anniversary") String contactAnniv);

    @POST(ServerApi.UPDATE_CONTACT)
    Call<ResponseBody> addContact(@Query("key") String key, @Query("user_id") String userId,@Query("contact_id") String contactId, @Query("contact_type") String contactType,  @Query("contact_rel") String contactRelation,@Query("contact_name") String contactName, @Query("contact_mob") String contactMobile, @Query("contact_email") String contactEmail, @Query("contact_add") String contactAddress, @Query("contact_dob") String contactDOB, @Query("contact_anniversary") String contactAnniv);

    @GET(ServerApi.FORGOT_PASS_MOBILE_VALIDATE)
    Call<ResponseBody> forgotPassMobileValidate(@Query("key") String key, @Query("contact_no") String mobile);

    @POST(ServerApi.FORGOT_PASS_RESET_PASSWORD)
    Call<ResponseBody> forgotPassResetPass(@Query("key") String key, @Query("contact_no") String mobile, @Query("password") String password);

    @POST(ServerApi.INVITE_TO_FRIEND)
    Call<ResponseBody> inviteToFriend(@Query("key") String key, @Query("user_id") String userId, @Query("contacts") String contacts);

    @POST(ServerApi.SEND_SMS)
    Call<ResponseBody> sendSMSSingle(@Query("key") String key, @Query("user_id") String userId, @Query("contact_type") String contactTypes,@Query("message") String message);

    @POST(ServerApi.SEND_SMS)
    Call<ResponseBody> sendSMSMultiple(@Query("key") String key, @Query("user_id") String userId, @Query("contact_no") String contacts,@Query("message") String message);

    @POST(ServerApi.GET_SEND_SMS)
    Call<ResponseBody> getSendSMS(@Query("key") String key, @Query("user_id") String userId);

    @FormUrlEncoded
    @POST(ServerApi.IMPORT_CONTACT)
    Call<ResponseBody> importContact(@Field("key")String key,@Field("user_id")String userId,@Field("import_contact_arr")String contactList);

    @GET(ServerApi.UPDATE_PROFILE)
    Call<ResponseBody> updateProfile(@Query("key") String key, @Query("user_id") String userId, @Query("user_name") String userName, @Query("user_email") String userEmail, @Query("secondary_contact") String secondaryContact, @Query("city_id") String cityId, @Query("locality_id") String localityId, @Query("address") String address);

    @POST(ServerApi.GET_CONTACTS)
    Call<ResponseBody> getContacts(@Query("key") String key, @Query("user_id") String userId);

    @POST(ServerApi.CHANGE_PASSWORD)
    Call<ResponseBody> changePassword(@Query("key") String key, @Query("user_id") String userId, @Query("old_pass") String oldPass, @Query("new_pass") String newPass);

    @POST(ServerApi.DELETE_CONTACT)
    Call<ResponseBody> deleteContact(@Query("key") String key, @Query("user_id") String userId, @Query("contact_id") String contactid);

}