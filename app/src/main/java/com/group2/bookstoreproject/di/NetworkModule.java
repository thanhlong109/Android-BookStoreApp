package com.group2.bookstoreproject.di;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.moshi.Moshi;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {
//
//    @Provides
//    @Singleton
//    public FirebaseAuth provideFireBaseAuth() {
//        return FirebaseAuth.getInstance();
//    }

//    @Provides
//    @Singleton
//    public DatabaseReference provideFireBaseDataBaseRef() {
//        return FirebaseDatabase.getInstance().getReference();
//    }

    @Provides
    @Singleton
    public FirebaseFirestore provideFirebaseFirestore() {
        return FirebaseFirestore.getInstance();
    }

//    @Provides
//    @Singleton
//    public FirebaseDatabase provideFireBaseDataBase() {
//        return FirebaseDatabase.getInstance();
//    }


//    @Provides
//    @Singleton
//    @Named("MainSite")
//    public Retrofit provideRetrofit(
//            OkHttpClient okHttpClient,
//            MoshiConverterFactory moshiConverterFactory) {
//
//        return new Retrofit.Builder()
//                .addConverterFactory(moshiConverterFactory)
//                .baseUrl(Constant.BASE_URL)
//                .client(okHttpClient)
//                .build();
//    }

//    @Provides
//    @Singleton
//    public OkHttpClient provideOKHttpClient(
//            HttpLoggingInterceptor httpLoggingInterceptor) {
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.interceptors().add(httpLoggingInterceptor);
//        return builder.build();
//    }

//    @Provides
//    @Singleton
//    public HttpLoggingInterceptor provideHttpLoggingInterceptor() {
//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        return httpLoggingInterceptor;
//    }


//    @Provides
//    @Singleton
//    public FirebaseStorage provideFirebaseStorage() {
//        return FirebaseStorage.getInstance();
//    }
}
