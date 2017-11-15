package com.example.konstantin.tnews.Dagger;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Konstantin on 15.11.2017.
 */

@Module
public class AppModule {
    private Context appContext;

    public AppModule(@NonNull Context context) {
        appContext = context;
    }

    @Provides
    @Singleton
    Context provideContext () {
        return appContext;
    }
}
