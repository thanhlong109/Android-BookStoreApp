package com.group2.bookstoreproject.data.api;
//import com.group2.bookstoreproject.data.model.FCMMessageNotification;
//import com.group2.bookstoreproject.data.model.FCMNotification;
import com.group2.bookstoreproject.data.model.FCMRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NotificationAPI {

    @POST("/v1/projects/bookstore-832c5/messages:send")
    @Headers({
            "Content-Type: application/json",
            "Host: fcm.googleapis.com"
    })
    Call<ResponseBody> postNotification(
            @Body FCMRequest messageData,
            @Header("Authorization") String authorization
    );
}

