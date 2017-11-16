package com.example.konstantin.tnews.Presenters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.konstantin.tnews.Dagger.DependencyInjector;
import com.example.konstantin.tnews.Model.DataManager;
import com.example.konstantin.tnews.POJO.NewsList.News;
import com.example.konstantin.tnews.R;
import com.example.konstantin.tnews.UI.NewsListFragment.NewsListAdapter;
import com.example.konstantin.tnews.UI.NewsListFragment.NewsListFragment;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by Konstantin on 09.11.2017.
 */

public class NewsListPresenter {
    @Inject DataManager dataManager;
    @Inject Context context;

    private WeakReference<NewsListFragment> bindedView;
    private final String TAG = "tNews";
    RecyclerView newsRecycler;
    SwipeRefreshLayout swipeRefreshLayout;

    public NewsListPresenter() {
        DependencyInjector.getComponent().inject(this);
    }

    // Прикрепление и открепление активити в зависимости от ЖЦ
    public void attachView(@NonNull NewsListFragment view) {
        bindedView = new WeakReference<NewsListFragment>(view);
        if (bindedView.get() != null) {
            newsRecycler = bindedView.get().getView().findViewById(R.id.newsListRecycler);
            swipeRefreshLayout = bindedView.get().getView().findViewById(R.id.swipe_refresh_layout);
            configureSwipeToRefresh();
        }
    }

    public void detachView() {
        bindedView = null;
        newsRecycler = null;
        swipeRefreshLayout = null;
    }

    // updateList: true - обновить с сервера, false вернуть закешированное
    public void getListOfNews(boolean updateList) {
        dataManager.getNewsList(getListObserver(), updateList); // запрос списка новостей
    }

    private Observer<List<News>> getListObserver() {
        // Observer для списка новостей
        // Тут обработка ошибок и действия после получения данных.
        return new Observer<List<News>>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "Observer<List<News>> метод onSubscribe(Disposable d) вызван");
            }

            @Override
            public void onNext(List<News> news) {
                // обновление списка новостей в UI
                if (swipeRefreshLayout != null && newsRecycler != null) {
                    configureRecyclerView(news);
                    swipeRefreshLayout.setRefreshing(false);
                }
                // кеширование полученного списка
                dataManager.updateListNewsCache(news);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, e.getLocalizedMessage());
                if (bindedView.get().getView() != null) {
                    Snackbar.make(bindedView.get().getView(), e.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "Observer<List<News>> метод onComplete() вызван");
            }
        };
    }

    private void configureRecyclerView(List<News> news) {
        NewsListAdapter newsListAdapter = new NewsListAdapter();
        newsRecycler.setAdapter(newsListAdapter);
        newsRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        newsListAdapter.setOrUpdateDataset(news);
    }

    private void configureSwipeToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                getListOfNews(true);
            }
        });
    }
}
