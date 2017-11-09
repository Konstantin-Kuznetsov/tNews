package com.example.konstantin.tnews.Presenters;

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
}
