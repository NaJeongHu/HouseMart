package com.example.publicdatacompetition;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RESTApi {

    @Headers(value = "Content-Type: application/json")
//    @POST("/loginRequest")
//    Call<List<Map<String,Object>>> login(@Body RequestBody body);

    @GET("/loginRequest")
    Call<Map<String,Object>> login(@Query("id") String id,
                                           @Query("password") String password);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://122.37.239.49:9000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
