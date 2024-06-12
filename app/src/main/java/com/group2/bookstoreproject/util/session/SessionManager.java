package com.group2.bookstoreproject.util.session;

import android.util.Log;

import com.group2.bookstoreproject.data.sharePreference.AppSharePreference;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.lang.reflect.Type;
import javax.inject.Inject;

public class SessionManager {

    private final AppSharePreference appSharePreference;

    public static final String NULL_DATA = "NULL_DATA";

    @Inject
    public SessionManager(AppSharePreference appSharePreference) {
        this.appSharePreference = appSharePreference;
    }



//    public User getLoggedInUser() {
//
//    }
}
