package com.example.konstantin.tnews.Dagger;

import android.support.annotation.NonNull;

import com.example.konstantin.tnews.Presenters.NewsDetailsPresenter;
import com.example.konstantin.tnews.Presenters.NewsListPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Konstantin on 09.11.2017.
 */

@Module
public class PresentersModule {
    @Provides
    @NonNull
    @Singleton
    public NewsListPresenter provideNewsListPresenter() {
        return new NewsListPresenter();
    }

    @Provides
    @NonNull
    @Singleton
    public NewsDetailsPresenter provideNewsDetailsPresenter() {
        return new NewsDetailsPresenter();
    }
}
