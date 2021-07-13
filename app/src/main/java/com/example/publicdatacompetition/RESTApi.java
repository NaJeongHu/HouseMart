package com.example.publicdatacompetition;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RESTApi {

    @Headers(value = "Content-Type: application/json")
    @POST(value = "login")
    Call<auth> login(@Body RequestBody body);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://52.79.236.212:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
