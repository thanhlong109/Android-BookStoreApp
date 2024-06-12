package com.group2.bookstoreproject.di;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;

import com.group2.bookstoreproject.data.sharePreference.AppSharePreference;
import com.group2.bookstoreproject.util.session.SessionManager;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Provides
    @Singleton
    public Context provideApplicationContext(@ApplicationContext @NonNull Context applicationContext) {
        return applicationContext;
    }

    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences(@ApplicationContext @NonNull Context context) {
        return context.getSharedPreferences(AppSharePreference.APP_SHARE_KEY, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    public AppSharePreference provideAppSharePreference(@ApplicationContext @NonNull Context context) {
        return new AppSharePreference(context);
    }

    @Provides
    @Singleton
    public SessionManager provideSessionManager(AppSharePreference appSharePreference) {
        return new SessionManager(appSharePreference);
    }
}
