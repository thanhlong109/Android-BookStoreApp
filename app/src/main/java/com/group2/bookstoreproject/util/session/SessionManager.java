package com.group2.bookstoreproject.util.session;

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
        if (user != null) {
            String userJson = jsonAdapter.toJson(user);
            appSharePreference.putString(KEY_USER, userJson);
        }
    }

    public User getLoggedInUser() {
        String userJson = appSharePreference.getString(KEY_USER, null);
        if (userJson != null) {
            try {
                return jsonAdapter.fromJson(userJson);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public User getAdmin(){
        if(AdminInstance == null){
            AdminInstance = new User();
            AdminInstance.setAvatar("");
            AdminInstance.setEmail("admin1@gmail.com");
            AdminInstance.setUserId("admin1@gmail.com");
        }
        return AdminInstance;
    }


    public void clearUser() {
        appSharePreference.putString(KEY_USER, null);
    }
}
