package com.example.publicdatacompetition;

import com.example.publicdatacompetition.Model.Contract;
import com.example.publicdatacompetition.Model.House;
import com.example.publicdatacompetition.Model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface RESTApi {

    @Headers(value = "Content-Type: application/json")
//    @POST("/loginRequest")
//    Call<List<Map<String,Object>>> login(@Body RequestBody body);

    @POST("/loginRequest")
    Call<User> login(
            @Query("id") String id,
            @Query("password") String password);

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

//    @Multipart
//    @POST("/write/board2")
//    Call<ResponseBody> uploadHouse(
//            @Query("userId") String userId,
//            @Query("residence_name") String residence_name,
//            @Query("code") String code,
//            @Query("address") String address,
//            @Query("sido") String sido,
//            @Query("sigungoo") String sigungoo,
//            @Query("dongri") String dongri,
//            @Query("date") String date,
//            @Query("allnumber") String allnumber,
//            @Query("parkingnumber") String parkingnumber,
//            @Query("contact") String contact,
//            @Query("dong") Integer dong,
//            @Query("ho") Integer ho,
//            @Query("net_leaseable_area") Double net_leaseable_area,
//            @Query("leaseable_area") Double leaseable_area,
//            @Query("residence_type") String residence_type,
//            @Query("sale_type") String sale_type,
//            @Query("sale_price") Long sale_price,
//            @Query("monthly_price") Long monthly_price,
//            @Query("admin_expenses") Long admin_expenses,
//            @Query("provisional_down_pay_per") Integer provisional_down_pay_per,
//            @Query("down_pay_per") Integer down_pay_per,
//            @Query("intermediate_pay_per") Integer intermediate_pay_per,
//            @Query("balance_per") Integer balance_per,
//            @Query("room_num") Integer room_num,
//            @Query("toilet_num") Integer toilet_num,
//            @Query("middle_door") boolean middle_door,
//            @Query("air_conditioner") boolean air_conditioner,
//            @Query("refrigerator") boolean refrigerator,
//            @Query("kimchi_refrigerator") boolean kimchi_refrigerator,
//            @Query("closet") boolean closet,
//            @Query("oven") boolean oven,
//            @Query("induction") boolean induction,
//            @Query("airsystem") boolean airsystem,
//            @Query("nego") boolean nego,
//            @Query("short_description") String short_description,
//            @Query("long_description") String long_description,
//            @Query("apartment_description") String apartment_description,
//            @Query("porch_description") String porch_description,
//            @Query("livingroom_description") String livingroom_description,
//            @Query("kitchen_description") String kitchen_description,
//            @Query("room1_description") String room1_description,
//            @Query("room2_description") String room2_description,
//            @Query("room3_description") String room3_description,
//            @Query("toilet1_description") String toilet1_description,
//            @Query("toilet2_description") String toilet2_description,
//            @Query("movedate") String movedate,
//            @Part MultipartBody.Part file1,
//            @Part MultipartBody.Part file2);

    @POST("/deal/getSalesOfferForDeal")
    Call<House> getContractHouseInfo(
            @Query("houseIdx") Long idx);

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
            @Query("sale_prices") String sale_prices,
            @Query("monthly_prices") String monthly_prices,
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


    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://122.37.239.49:9000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
