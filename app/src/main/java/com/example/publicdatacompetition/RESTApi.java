package com.example.publicdatacompetition;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
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
    Call<ResponseBody> login(
            @Query("id") String id,
            @Query("password") String password);

    @Multipart
    @POST("/joinRequest")
    Call<ResponseBody> joinRequest(
            @Query("id") String id,
            @Query("password") String password,
            @Query("passwordConfirm") String passwordConfirm,
            @Query("phoneNumber") String phoneNumber,
            @Query("name") String name,
            @Query("nickname") String nickname,
            @Query("idNum") String idNum,
            @Part MultipartBody.Part file);

    @POST("/board/get/list")
    Call<List<PermittedHouse>> getList();

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://122.37.239.49:9000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
