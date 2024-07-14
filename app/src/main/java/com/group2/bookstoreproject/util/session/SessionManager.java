package com.group2.bookstoreproject.util.session;

import android.util.Log;

import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.data.sharePreference.AppSharePreference;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;

import javax.inject.Inject;

public class SessionManager {
    private static final String KEY_USER = "key_user";
    private  AppSharePreference appSharePreference;
    public static final String NULL_DATA = "NULL_DATA";
    private  JsonAdapter<User> jsonAdapter;
    private static User AdminInstance = null;

    private static SessionManager Instance = null;

    @Inject
    public SessionManager(AppSharePreference appSharePreference) {
        if(Instance ==null){
            this.appSharePreference = appSharePreference;
            Moshi moshi = new Moshi.Builder().build();
            this.jsonAdapter = moshi.adapter(User.class);
            this.Instance = this;
        }
    }

    public static SessionManager getInstance() {
        return Instance;
    }

    public void saveUser(User user) {
        Log.d("test",""+user.toString());
        if (user != null) {
            String userJson = Instance.jsonAdapter.toJson(user);
            Instance.appSharePreference.putString(KEY_USER, userJson);
        }
    }

    public User getLoggedInUser() {
        String userJson = Instance.appSharePreference.getString(KEY_USER, null);
        if (userJson != null) {
            try {
                return Instance.jsonAdapter.fromJson(userJson);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public User getAdmin(){
        if(AdminInstance == null){
            AdminInstance = new User();
            AdminInstance.setAvatar("https://firebasestorage.googleapis.com/v0/b/bookstore-832c5.appspot.com/o/images%2Fuser-4.jpg?alt=media&token=f43cb1a5-3359-4f15-a9d3-dc8f8ac0befa");
            AdminInstance.setEmail("admin@gmail.com");
            AdminInstance.setUserId("admin@gmail.com");
            AdminInstance.setFullName("BookStore");
            AdminInstance.setDeviceToken("iuvnaoeimov");
        }
        return AdminInstance;
    }


    public void clearUser() {
        appSharePreference.remove(KEY_USER); // Remove the key from SharedPreferences
    }
}
