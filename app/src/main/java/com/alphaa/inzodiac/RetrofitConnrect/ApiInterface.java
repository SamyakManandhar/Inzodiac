package com.alphaa.inzodiac.RetrofitConnrect;


import com.alphaa.filter.Filter;
import com.alphaa.inzodiac.addminustoken.model.TokenAddPojo;
import com.alphaa.inzodiac.billing.BillingParm;
import com.alphaa.inzodiac.retrofit_chatting_notification.RequestNotificaton;
import com.alphaa.inzodiac.tabmodule.activity.detailmodule.DetailModel;
import com.alphaa.inzodiac.tabmodule.activity.detailmodule.SendGameRequest;
import com.alphaa.inzodiac.tabmodule.activity.detailmodule.UserFavoriteParm;
import com.alphaa.inzodiac.tabmodule.fragment.filtermodule.Countryid;
import com.alphaa.inzodiac.tabmodule.fragment.likemodule.FavoriteParm;
import com.alphaa.inzodiac.tabmodule.fragment.menumodule.MenuParm;
import com.alphaa.inzodiac.tabmodule.fragment.menumodule.UserMenuModel;
import com.alphaa.inzodiac.ui.activity.aboutself.aboutmodel.AboutSelfCategoryPojo;
import com.alphaa.inzodiac.ui.activity.contactus.contactusmodel.ContactUsPojo;
import com.alphaa.inzodiac.ui.activity.detailaboyou.model.SendQuizRequest;
import com.alphaa.inzodiac.ui.activity.editphonemodule.UpdatePhoneParam;
import com.alphaa.inzodiac.ui.activity.loginmodule.LoginParm;
import com.alphaa.inzodiac.ui.activity.notification.notificationmodel.NotificationModel;
import com.alphaa.inzodiac.ui.activity.phoneloginmodule.PhoneLoginParam;
import com.alphaa.inzodiac.ui.activity.phoneverification.SamePhoneCheckParam;
import com.alphaa.inzodiac.ui.signupmodel.SignupDatum;
import com.alphaa.inzodiac.ui.signupmodel.SignupPojo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by mindiii on 13/11/18.
 */

public interface ApiInterface {

    //UserCategoryUpdate

    @POST("Authentication/get_cities")
    Call<ResponseBody> get_cities(@Body Countryid jsonObject);


    @GET("Authentication/get_countries")
    Call<ResponseBody> get_countries();

    @GET("Match/get_category")
    Call<ResponseBody> get_category();


    @GET("Match/get_western_category")
    Call<ResponseBody> get_western_category();


    @GET("Match/get_chinese_category")
    Call<ResponseBody> get_chinese_category();


    //    @FormUrlEncoded
    @POST("Authentication/login")
    Call<ResponseBody> get_login(@Body LoginParm loginParm);

    @FormUrlEncoded
    @POST("Authentication/SocialLogin")
    Call<ResponseBody> SocialLogin(@Field("name") String name, @Field("email") String email, @Field("social") String social, @Field("Firebase_token") String Firebase_token);

    @FormUrlEncoded
    @POST("Authentication/UserDetails")
    Call<DetailModel> UserDetails(@Field("userId") String userId);


    @POST("Match/user_favorite")
    Call<ResponseBody> user_favorite(@Body UserFavoriteParm userFavoriteParm);

    @POST("Match/my_matches_list")
    Call<ResponseBody> my_matches_list(@Body FavoriteParm favoriteParm);


    @FormUrlEncoded
    @POST("Authentication/forget_password")
    Call<ResponseBody> forget_password(@Field("email") String email);


    @POST("Authentication/UserList")
    Call<Filter> UserList(@Body MenuParm menuParm);


    @POST("Authentication/UserList")
    Call<Filter> UserListFilter(@Body MenuParm str);

    /*@FormUrlEncoded
    @POST("Authentication/registration")
    Call<ResponseBody> get_signup(@Field("email") String email,
                                 @Field("password") String password,
                                 @Field("mobile") String mobile,
                                 @Field("Firebase_token") String Firebase_token
                                ,@Field("fullname") String fullname,
                                  @Field("lat") String lat,
                                  @Field("long") String longg);
*/

    @POST("Authentication/check_same_phone_number")
    Call<ResponseBody> checkSamePhoneNumber(@Body SamePhoneCheckParam params);

    @POST("Authentication/update_phone_no")
    Call<ResponseBody> updatePhoneNumber(@Body UpdatePhoneParam params);

    @POST("Authentication/login_phone")
    Call<ResponseBody> loginWithPhoneNumber(@Body PhoneLoginParam params);

    @Multipart
    @POST("Authentication/registration")
    Call<ResponseBody> get_signup(@Part("fullname") RequestBody fullname,
                                  @Part("email") RequestBody email,
                                  @Part("mobile") RequestBody mobile,
                                  @Part("password") RequestBody password,
                                  @Part("lat") RequestBody lat,
                                  @Part("long") RequestBody lon,
                                  @Part("Firebase_token") RequestBody Firebase_token
    );


    @Multipart
    @POST("Authentication/AboutSelf")
    Call<SignupPojo> AboutSelf(@Part("gender") RequestBody firstname,
                               @Part("dob") RequestBody dob,
                               @Part("ethnicity") RequestBody ethnicity,
                               @Part("bodytype") RequestBody bodytype,
                               @Part("height") RequestBody height,
                               @Part("haircolour") RequestBody haircolour,
                               @Part("eyecolour") RequestBody eyecolour,
                               @Part("hororscopetype") RequestBody hororscopetype,
                               @Part("married") RequestBody married,
                               @Part("children") RequestBody children,
                               @Part("language") RequestBody language,
                               @Part("smoke") RequestBody smoke,
                               @Part("drink") RequestBody drink,
                               @Part("about") RequestBody about,
                               @Part("orientation") RequestBody orientation,
                               @Part("intrested_in") RequestBody intrested_in,
                               @Part("looking_for") RequestBody looking_for,
                               @Part("user_id") RequestBody user_id,
                               @Part("country") RequestBody country,
                               @Part("city") RequestBody city,
                               @Part("western_zodaic") RequestBody western_zodaic,
                               @Part("chinese_zodaic") RequestBody chinese_zodaic,
                               @Part("pets") RequestBody pets,
                               @Part("religion") RequestBody religion,
                               @Part("date_food") RequestBody date_food,
                               @Part("education") RequestBody education,
                               @Part("my_work") RequestBody my_work,
                               @Part("relationship") RequestBody relationship,

                               @Part MultipartBody.Part... profilePic

//eyecolour:asdasd
    );





    //////////////////////////
    @POST("Game/send_game_request")
    Call<SendQuizRequest> sendGameRequest(@Body SendGameRequest sendGameRequest);


    @POST("Game/accept_game_request")
    Call<SendQuizRequest> acceptGameRequest(@Body SendGameRequest sendGameRequest);

    @POST("Authentication/UserCategoryUpdate")
    Call<AboutSelfCategoryPojo> userCategoryUpdate(@Body SendGameRequest sendGameRequest);




    /////
    @GET("Payment/get_premium_list")
    Call<ResponseBody> getplan_list();

    @GET("Payment/get_token_reward_list")
    Call<ResponseBody> getToken_list();

    @POST("Payment/Billing")
    Call<ResponseBody> billing_api(@Body BillingParm favoriteParm);



    ///////////////
    @FormUrlEncoded
    @POST("Payment/get_premium_list")
    Call<ResponseBody> getplan_list(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("Payment/get_token_reward_list")
    Call<ResponseBody> getToken_list(@Field("user_id") String user_id);


    @POST("Authentication/UserListfiltter")
    Call<ResponseBody> UserListUsingZodaic(@Body MenuParm menuParm);




    //============================================chat notification
    @Headers({"Authorization: key=AAAAVQKLLjY:APA91bGW02lQR6uWtwxMZL-sVcMxN3R4FzK-EbdYzCH1czvyEd1VpdxmCdSXAzPOhFIRf1AHBzGz2QonygvTJ2Up02Mt-mECVImYjyvwUq14eDOBGmrXnFisuMHXJ0fvMoBvhi9ZDGfg",
            "Content-Type:application/json"})
    @POST("send")
    Call<ResponseBody> sendNotificationOnChatting(@Body RequestNotificaton requestNotificaton);



    ///=====================================like request
    @POST("Match/send_like_request")
    Call<ResponseBody> sendLikeRequest(@Body UserFavoriteParm userFavoriteParm);


    @POST("Match/accept_like_request")
    Call<SendQuizRequest> acceptLikeRequest(@Body SendGameRequest sendGameRequest);


    @Multipart
    @POST("Authentication/update_profile_image")
    Call<ResponseBody> uploadProfileImage(@Part MultipartBody.Part user_id,
                                  @Part MultipartBody.Part profilepic);

    @POST("Authentication/delete_profile_image")
    Call<DetailModel> deleteUserProfile(@Body JsonObject jsonObject);






    @FormUrlEncoded
    @POST("Authentication/contact_us")
    Call<ContactUsPojo> contactUs(@Field("name") String name, @Field("phone_no") String phone_no, @Field("email") String email, @Field("subject") String subject,
                                  @Field("meassge") String meassge);


    @FormUrlEncoded
    @POST("Authentication/NotificationList")
    Call<NotificationModel> getNotification(@Field("user_id") String user_id);

    //@FormUrlEncoded
    @POST("Match/user_dislike")
    Call<ResponseBody> dislikeUser(@Body UserFavoriteParm userFavoriteParm);


    @FormUrlEncoded
    @POST("Authentication/UserTokenUpdate")
    Call<TokenAddPojo> addAndSubtractToken(@Field("user_id") String user_id,
                                           @Field("token") String token,
                                           @Field("type") String type);

}


