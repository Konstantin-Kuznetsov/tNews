package com.example.konstantin.tnews.Dagger;

import android.support.annotation.NonNull;

import com.example.konstantin.tnews.Model.tNewsApi;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Konstantin on 09.11.2017.
 */

@Module
public class UtilsModule {
    private static final String BASE_URL = "https://api.tinkoff.ru/v1/";

    @Provides
    @NonNull
    @Singleton
    public Gson provideGson() {
        return new Gson();
    }

    @Provides
    @NonNull
    @Singleton
    public Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @NonNull
    @Singleton
    public tNewsApi.tinkoffNewsInterface provideNewsApi(Retrofit retrofitInstance) {
        return retrofitInstance.create(tNewsApi.tinkoffNewsInterface.class);
    }
}
