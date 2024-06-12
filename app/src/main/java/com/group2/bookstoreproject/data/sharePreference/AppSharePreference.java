package com.group2.bookstoreproject.data.sharePreference;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import javax.inject.Inject;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;


public class AppSharePreference {

    public static final String APP_SHARE_KEY = "com.group2.bookstoreproject";
    private final SharedPreferences prefs;
    private final Moshi moshi;

    @Inject
    public AppSharePreference(Context context) {
        this.prefs = context.getSharedPreferences(APP_SHARE_KEY, Context.MODE_PRIVATE);
        this.moshi = new Moshi.Builder().build();
    }

    private String encode(String normalText) {
        return Base64.encodeToString(normalText.getBytes(), Base64.DEFAULT);
    }

    private String decode(String encodedText) {
        return new String(Base64.decode(encodedText.getBytes(), Base64.DEFAULT));
    }

    // Boolean
    public void putBoolean(String key, boolean value) {
        prefs.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return prefs.getBoolean(key, defValue);
    }

    // Float
    public void putFloat(String key, float value) {
        prefs.edit().putFloat(key, value).apply();
    }

    public float getFloat(String key, float defValue) {
        return prefs.getFloat(key, defValue);
    }

    // Int
    public void putInt(String key, int value) {
        prefs.edit().putInt(key, value).apply();
    }

    public int getInt(String key, int defValue) {
        return prefs.getInt(key, defValue);
    }

    // Long
    public void putLong(String key, long value) {
        prefs.edit().putLong(key, value).apply();
    }

    public long getLong(String key, long defValue) {
        return prefs.getLong(key, defValue);
    }

    // String
    public void putString(String key, String value) {
        prefs.edit().putString(key, encode(value)).apply();
    }

    public String getString(String key, String defValue) {
        String encodedValue = prefs.getString(key, null);
        return encodedValue != null ? decode(encodedValue) : defValue;
    }

    // Model
    public <T> void putModel(String key, T model, Class<T> classOfT) {
        if (model != null) {
            JsonAdapter<T> jsonAdapter = moshi.adapter(classOfT);
            String json = jsonAdapter.toJson(model);
            prefs.edit().putString(key, encode(json)).apply();
        } else {
            prefs.edit().putString(key, "").apply();
        }
    }

    public <T> T getModel(String key, Class<T> classOfT) {
        String jsonObjects = decode(prefs.getString(key, ""));
        if (!jsonObjects.isEmpty()) {
            JsonAdapter<T> jsonAdapter = moshi.adapter(classOfT);
            try {
                return jsonAdapter.fromJson(jsonObjects);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}