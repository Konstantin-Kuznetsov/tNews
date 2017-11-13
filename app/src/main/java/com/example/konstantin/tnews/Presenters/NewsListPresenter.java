package com.example.konstantin.tnews.Presenters;

import android.support.annotation.NonNull;

import com.example.konstantin.tnews.Dagger.DependencyInjector;
import com.example.konstantin.tnews.Model.DataManager;
import com.example.konstantin.tnews.UI.NewsListFragment.NewsListFragment;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

/**
 * Created by Konstantin on 09.11.2017.
 */

public class NewsListPresenter {
    @Inject DataManager dataManager;

    private WeakReference<NewsListFragment> bindedView;

    public NewsListPresenter() {
        DependencyInjector.getComponent().inject(this);
    }

    // Прикрепление и открепление активити в зависимости от ЖЦ
    public void attachView(@NonNull NewsListFragment view) {
        bindedView = new WeakReference<NewsListFragment>(view);

        dataManager.getNewsList(); // сетевой запрос списка новостей
    }

    public void detachView() {
        bindedView = null;
    }

}
