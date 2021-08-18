package com.howsmart.housemart.Notifications;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FirebaseApi {
    @Headers({
                "Content-Type:application/json",
                "Authorization:key=AAAAbuwt3jI:APA91bERqOlrbEeTLG2moVIJdVgWoSUp5itZqy_w1kKGo9VlDPOo_Tg31HZ9h-yhZ9GdqOkoJ9RvXdY3FiULpYhYMDGO5gtUqllJRD2RDziY2ddQui2y5BlU26aZ-kG1QRbGR-VnV98H"
            })

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender sender);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://fcm.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
