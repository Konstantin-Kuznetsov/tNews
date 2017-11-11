package com.example.konstantin.tnews.Dagger;

import android.support.annotation.NonNull;

import com.example.konstantin.tnews.Model.CacheHelper;
import com.example.konstantin.tnews.Model.DataManager;
import com.example.konstantin.tnews.Model.RESTDataProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Konstantin on 09.11.2017.
 */

@Module
public class DataProvidersModule {
    @Provides
    @NonNull
    @Singleton
    public DataManager provideDataManager() {
        return new DataManager();
    }

    @Provides
    @NonNull
    @Singleton
    public CacheHelper provideCacheHelper() {
        return new CacheHelper();
    }

    @Provides
    @NonNull
    @Singleton
    public RESTDataProvider provideRestDataProvider() {
        return new RESTDataProvider();
    }
}
