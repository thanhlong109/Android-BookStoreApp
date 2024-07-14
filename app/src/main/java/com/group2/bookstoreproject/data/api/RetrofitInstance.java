package com.group2.bookstoreproject.data.api;
import com.squareup.moshi.Moshi;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://fcm.googleapis.com";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            Moshi moshi = new Moshi.Builder().build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build();
        }
        return retrofit;
    }

    public static NotificationAPI getNotificationAPI() {
        return getRetrofitInstance().create(NotificationAPI.class);
    }
}