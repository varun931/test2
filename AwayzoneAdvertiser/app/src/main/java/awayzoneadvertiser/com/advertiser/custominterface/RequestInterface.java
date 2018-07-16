package awayzoneadvertiser.com.advertiser.custominterface;
import awayzoneadvertiser.com.advertiser.culturelist.ListResponse;
import awayzoneadvertiser.com.advertiser.dashboard.DashBoardDataResponse;
import awayzoneadvertiser.com.advertiser.gettersetter.AddListResponse;
import awayzoneadvertiser.com.advertiser.gettersetter.ChatListResponse;
import awayzoneadvertiser.com.advertiser.gettersetter.CultureDataResponse;
import awayzoneadvertiser.com.advertiser.gettersetter.EditProfileResponse;
import awayzoneadvertiser.com.advertiser.gettersetter.HalfRegisterationResponse;
import awayzoneadvertiser.com.advertiser.gettersetter.InterestDataResponse;
import awayzoneadvertiser.com.advertiser.gettersetter.LoginResponse;
import awayzoneadvertiser.com.advertiser.gettersetter.PostChatResponse;
import awayzoneadvertiser.com.advertiser.gettersetter.ServerResponse;
import awayzoneadvertiser.com.advertiser.gettersetter.SubscriptionListResponse;
import awayzoneadvertiser.com.advertiser.gettersetter.TicketListResponse;
import awayzoneadvertiser.com.advertiser.gettersetter.UserProfileResponse;
import awayzoneadvertiser.com.advertiser.interestlist.InterestListResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RequestInterface {

    @FormUrlEncoded
    @POST("half_reg")
    Call<HalfRegisterationResponse> halfReg(@Field("email") String email, @Field("password") String id, @Field("notify_id") String notifyid, @Field("reg_type") String regtype);

    @FormUrlEncoded
    @POST("adverLogin")
    Call<LoginResponse> login(@Field("email") String email, @Field("password") String password, @Field("notify_id") String notifyid, @Field("login_type") String loginType);

    @FormUrlEncoded
    @POST("forgot")
    Call<HalfRegisterationResponse> forgetpassword(@Field("email") String email);

    @GET("get_culture")
    Call<CultureDataResponse> getCulture();

    @FormUrlEncoded
    @POST("get_interest")
    Call<InterestDataResponse> getInterest(@Field("culture") String culture);

    @FormUrlEncoded
    @POST("fullReg")
    Call<HalfRegisterationResponse> fullReg(@Field("user_id") int id, @Field("full_name") String name, @Field("organization_name") String orgname, @Field("contact_no") String contactname, @Field("email") String email, @Field("lati") String lat, @Field("longi") String lang, @Field("culture") String culture, @Field("interest") String interest);

    @Multipart
    @POST("upload_image")
    Call<ServerResponse> uploadFile(@Part MultipartBody.Part file,
                                    @Part("file") RequestBody name, @Part("id") int id);
    @Multipart
    @POST("defaultProfile")
    Call<ServerResponse> uploaddefaultFile(@Part MultipartBody.Part file,@Part("file") RequestBody name, @Part("user_id") int id);

    @FormUrlEncoded
    @POST("changePassword")
    Call<HalfRegisterationResponse> changePassword(@Field("user_id") int userid, @Field("old_password") String oldpassword, @Field("new_password") String newpassword);

    @FormUrlEncoded
    @POST("dashboardDay")
    Call<DashBoardDataResponse> dashboard(@Field("user_id") int userid);

    @FormUrlEncoded
    @POST("dashboard")
    Call<DashBoardDataResponse> dashboardfilter(@Field("user_id") int userid,@Field("type") String stringtype,@Field("record_type") String recordtype);

    @FormUrlEncoded
    @POST("adverProfile")
    Call<UserProfileResponse> adverprofile(@Field("user_id") int userid);

    //updateProfile
    @FormUrlEncoded
    @POST("updateProfile")
    Call<EditProfileResponse> updatefullReg(@Field("user_id") int id, @Field("full_name") String name, @Field("organization_name") String orgname, @Field("contact_no") String contactname, @Field("email") String email, @Field("lati") String lat, @Field("longi") String lang, @Field("culture") String culture, @Field("interest") String interest, @Field("user_description") String userdescription, @Field("alias_name") String alias_name);

   //SubscriptionListResponse
    @FormUrlEncoded
    @POST("planList")
    Call<SubscriptionListResponse> planlist(@Field("user_id") int id);

    @FormUrlEncoded
    @POST("myAds")
    Call<AddListResponse> addlist(@Field("user_id") int id);

    @FormUrlEncoded
    @POST("checkLoginTime")
    Call<HalfRegisterationResponse> logout(@Field("user_id") int id);

    @GET("get_culture")
    Call<ListResponse> getCulturedata();

    @FormUrlEncoded
    @POST("onOffAds")
    Call<HalfRegisterationResponse> addlist(@Field("user_id") int id,@Field("id") int addid);

    @FormUrlEncoded
    @POST("createChatTicket")
    Call<HalfRegisterationResponse> createnewticket(@Field("advertiser_id") int id,@Field("priority") String priority, @Field("title") String  title, @Field("description") String  description);

    @FormUrlEncoded
    @POST("ticketList")
    Call<TicketListResponse> ticketlist(@Field("advertiser_id") int id);

    @FormUrlEncoded
    @POST("adverChat")
    Call<ChatListResponse> adverchatlist(@Field("advertiser_id") int adid, @Field("chat_ticket_id") int chatid);

    @FormUrlEncoded
    @POST("adverChatMessage")
    Call<PostChatResponse> postchat(@Field("advertiser_id") int adid, @Field("chat_ticket_id") int chatid, @Field("message") String message);

    @FormUrlEncoded
    @POST("openCloseTicket")
    Call<PostChatResponse> openclose(@Field("advertiser_id") int adid, @Field("chat_ticket_id") int chatid);

    @FormUrlEncoded
    @POST("saveAdverPlan")
    Call<HalfRegisterationResponse> payment(@Field("advertiser_id") int adid, @Field("subscription_id") int subscriptionid,@Field("amount") int amount,@Field("subscription_paypal_id") String subscriptionpaypal,@Field("no_of_ads") int noofads);

    @Multipart
    @POST("createAds")
    Call<ServerResponse> createadd(@Part MultipartBody.Part file, @Part("file") RequestBody name, @Part("advertiser_id")int id, @Part("title")String title, @Part("description")String description);

    @FormUrlEncoded
    @POST("createAdDate")
    Call<HalfRegisterationResponse> creataddfully(@Field("ad_id") int addid, @Field("start_date")String startdate, @Field("end_date")String enddate);

   /* @FormUrlEncoded
    @POST("createAds")
    Call<HalfRegisterationResponse> createadd(@Field("advertiser_id")int id,@Field("title")String title,@Field("description")String description,@Field("start_date")String startdate,@Field("end_date")String enddate);
*/
    @GET("getInterest")
    Call<InterestListResponse> getInterest();

    @FormUrlEncoded
    @POST("flag")
    Call<ListResponse> flag(@Field("cul_name")String userid);
}
