package com.group2.bookstoreproject.data.repositoryImpl;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.data.api.RetrofitInstance;
//import com.group2.bookstoreproject.data.model.FCMNotification;
import com.group2.bookstoreproject.data.model.FCMRequest;
import com.group2.bookstoreproject.data.model.Notification;
import com.group2.bookstoreproject.data.repository.NotificationRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationRepositoryImpl extends BaseRepositoryImpl<Notification> implements NotificationRepository {
    private static final String TAG = "notification repository";
    private static final String ACCESS_TOKEN_PREFIX = "Bearer ";
    private static final String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
    private static final List<String> SCOPES = Collections.singletonList(MESSAGING_SCOPE);
    private String accessToken;

    private static final String COLLECTION_PATH = "notifications";

    @Override
    public void sendNotification(FCMRequest notification, Context context) {
        fetchAccessToken(context, notification);
    }

    private void fetchAccessToken(Context context, FCMRequest notification) {
        new Thread(() -> {
            try {
                String accessToken = getAccessToken(context);
                sendNotification(notification, accessToken);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private String getAccessToken(Context context) throws IOException {
        InputStream inputStream = context.getResources().openRawResource(R.raw.account_service);
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(inputStream).createScoped(SCOPES);
        googleCredentials.refresh();
        return ACCESS_TOKEN_PREFIX + googleCredentials.getAccessToken().getTokenValue();
    }

    private void sendNotification(FCMRequest notification, String accessToken) {
        Log.d(TAG, accessToken);
        Call<ResponseBody> call = RetrofitInstance.getNotificationAPI().postNotification(notification, accessToken);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Notification sent: " + notification);
                } else {
                    try {
                        // Chuyển đổi response.errorBody() thành chuỗi và ghi log
                        String errorBody = response.errorBody().string();
                        Log.e(TAG, "Failed to send notification. Error: " + errorBody);
                    } catch (IOException e) {
                        Log.e(TAG, "Failed to read error body", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "Failed to send notification. Exception: " + t.getMessage());

            }
        });
    }
    @Override
    protected String getCollectionPath() {
        return COLLECTION_PATH;
    }

}
