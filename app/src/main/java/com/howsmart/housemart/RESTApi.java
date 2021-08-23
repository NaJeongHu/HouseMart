package com.howsmart.housemart;

import com.howsmart.housemart.Model.Contract;
import com.howsmart.housemart.Model.House;
import com.howsmart.housemart.Model.MyContract;
import com.howsmart.housemart.Model.MyHouse;
import com.howsmart.housemart.Model.PermittedHouse;
import com.howsmart.housemart.Model.ProvisionalHouse;
import com.howsmart.housemart.Model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RESTApi {

    @Headers(value = "Content-Type: application/json")
//    @POST("/loginRequest")
//    Call<List<Map<String,Object>>> login(@Body RequestBody body);

    @POST("/loginRequest")
    Call<User> login(
            @Query("id") String id,
            @Query("password") String password);

    @POST("/member/get/one")
    Call<User> getUserInfo(
            @Query("userId") String userId);

    @Multipart
    @POST("/joinRequest")
    Call<ResponseBody> joinRequest(
            @Query("userId") String userId,
            @Query("password") String password,
            @Query("passwordConfirm") String passwordConfirm,
            @Query("phoneNumber") String phoneNumber,
            @Query("name") String name,
            @Query("nickname") String nickname,
            @Query("idNum") String idNum,
            @Query("firebaseId") String firebaseId,
            @Part MultipartBody.Part file);

    @Multipart
    @POST("/test/multifile")
    Call<ResponseBody> uploadHouse1(
            @Part List<MultipartBody.Part> fileList);

    @POST("/board/get/list")
    Call<List<PermittedHouse>> getList();

    @POST("/deal/contract/contractList")
    Call<List<ProvisionalHouse>> getProvisionalHouseList();

    @POST("/board/get/OneByIdx")
    Call<HouseInfoDetail> getDetailInfo(
            @Query("idx") Long idx);

    @Multipart
    @POST("/write/board2")
    Call<ResponseBody> uploadHouse(
            @Query("userId") String userId,
            @Query("residence_name") String residence_name,
            @Query("code") String code,
            @Query("address") String address,
            @Query("sido") String sido,
            @Query("sigungoo") String sigungoo,
            @Query("dongri") String dongri,
            @Query("date") String date,
            @Query("allnumber") Integer allnumber,
            @Query("parkingnumber") Integer parkingnumber,
            @Query("contact") String contact,
            @Query("dong") Integer dong,
            @Query("ho") Integer ho,
            @Query("net_leaseable_area") Double net_leaseable_area,
            @Query("leaseable_area") Double leaseable_area,
            @Query("residence_type") String residence_type,
            @Query("sale_type") String sale_type,
            @Query("sale_price") Long sale_price,
            @Query("monthly_price") Long monthly_price,
            @Query("admin_expenses") Long admin_expenses,
            @Query("provisional_down_pay_per") Integer provisional_down_pay_per,
            @Query("down_pay_per") Integer down_pay_per,
            @Query("intermediate_pay_per") Integer intermediate_pay_per,
            @Query("balance_per") Integer balance_per,
            @Query("room_num") Integer room_num,
            @Query("toilet_num") Integer toilet_num,
            @Query("middle_door") boolean middle_door,
            @Query("air_conditioner") boolean air_conditioner,
            @Query("refrigerator") boolean refrigerator,
            @Query("kimchi_refrigerator") boolean kimchi_refrigerator,
            @Query("closet") boolean closet,
            @Query("oven") boolean oven,
            @Query("induction") boolean induction,
            @Query("airsystem") boolean airsystem,
            @Query("nego") boolean nego,
            @Query("short_description") String short_description,
            @Query("long_description") String long_description,
            @Query("apartment_description") String apartment_description,
            @Query("porch_description") String porch_description,
            @Query("livingroom_description") String livingroom_description,
            @Query("kitchen_description") String kitchen_description,
            @Query("room1_description") String room1_description,
            @Query("room2_description") String room2_description,
            @Query("room3_description") String room3_description,
            @Query("toilet1_description") String toilet1_description,
            @Query("toilet2_description") String toilet2_description,
            @Query("movedate") String movedate,
            @Part MultipartBody.Part file1,
            @Part MultipartBody.Part file2,
            @Part MultipartBody.Part file3,
            @Part MultipartBody.Part file4,
            @Part MultipartBody.Part file5,
            @Part MultipartBody.Part file6,
            @Part MultipartBody.Part file7,
            @Part MultipartBody.Part file8,
            @Part MultipartBody.Part file9);

    @POST("/deal/getSalesOfferForDeal")
    Call<House> getContractHouseInfo(
            @Query("idx") Long idx);

    @POST("/deal/findMemberByPhoneNum")
    Call<User> getContractUserInfo(
            @Query("phonenumber") String phonenumber);

    @POST("deal/contract/write")
    Call<Long> writeContract(
            @Query("offerIdx") Long idx,
            @Query("sale_type") String sale_type,
            @Query("address_apartment") String address_apartment,
            @Query("purpose") String purpose,
            @Query("area") String area,
            @Query("sale_prices") Long sale_prices,
            @Query("monthly_prices") Long monthly_prices,
            @Query("provisional_down_pay") String provisional_down_pay,
            @Query("down_pay") String down_pay,
            @Query("intermediate_pay") String intermediate_pay,
            @Query("balance") String balance,
            @Query("special") String special,
            @Query("date") String date,
            @Query("id1") Long id1,
            @Query("id2") Long id2);

    @POST("/deal/contract/get")
    Call<Contract> getContract(
            @Query("idx") Long idx);

    @POST("deal/contract/provisional-pay")
    Call<Boolean> successContract(
            @Query("idx") Long idx);
//
    @Multipart
    @POST("register/certification")
    Call<ResponseBody> uploadCertificatioin(
            @Query("userId") String userId,
            @Part MultipartBody.Part certification);

    @POST("/alter/memberInfo")
    Call<ResponseBody> changePrivateinfo(
            @Query("userId") String userId,
            @Query("alterType") String alterType,
            @Query("nickName") String nickName,
            @Query("birth") String Birth,
            @Query("existingPass") String existingPass,
            @Query("newPass") String newPass,
            @Query("confirmPass") String confirmPass
    );

    @Multipart
    @POST("/alter/memberImg")
    Call<ResponseBody> changePicture(
            @Query("userId") String userId,
            @Part MultipartBody.Part picture);

    @POST("/member/get/list")
    Call<List<MyHouse>> getMySellList(
            @Query("userId") String userId);

    @POST("/member/get/allContract")
    Call<List<MyContract>> getAllContractList(
            @Query("userId") String userId);

    @POST("/member/get/intermediaryList")
    Call<List<MyContract>> getBrokerContractList(
            @Query("userId") String userId);

    @POST("/deal/contract/connection")
    Call<Boolean> startContract(
            @Query("idx") Long idx,
            @Query("userId") String userId
    );


    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://ec2-3-37-133-249.ap-northeast-2.compute.amazonaws.com:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
