package com.example.konstantin.tnews.Presenters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.konstantin.tnews.Dagger.DependencyInjector;
import com.example.konstantin.tnews.Model.DataManager;
import com.example.konstantin.tnews.POJO.NewsList.News;
import com.example.konstantin.tnews.R;
import com.example.konstantin.tnews.UI.MainActivity.NewsActivity;
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

    private WeakReference<NewsListFragment> bindedFragment;
    private WeakReference<NewsActivity> bindedActivity;
    private final String TAG = "tNews";
    private RecyclerView newsRecycler;
    private TextView errorText;
    private SwipeRefreshLayout swipeRefreshLayout;

    public NewsListPresenter() {
        DependencyInjector.getComponent().inject(this);
    }

    // Прикрепление и открепление активити в зависимости от ЖЦ
    public void attachView(@NonNull NewsListFragment view) {
        bindedFragment = new WeakReference<NewsListFragment>(view);
        if (bindedFragment.get() != null) {
            newsRecycler = bindedFragment.get().getView().findViewById(R.id.newsListRecycler);
            swipeRefreshLayout = bindedFragment.get().getView().findViewById(R.id.swipe_refresh_layout_list);
            errorText = bindedFragment.get().getView().findViewById(R.id.errorTextList);
            configureSwipeToRefresh();
        }
    }

    public void attachActivity(@NonNull NewsActivity view) {
        bindedActivity = new WeakReference<NewsActivity>(view);
    }

    public void detachActivity() {
        bindedActivity = null;
    }

    public void detachView() {
        bindedFragment = null;
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
                errorText.setVisibility(View.GONE);
                Log.i(TAG, "Observer<List<News>> метод onSubscribe(Disposable d) вызван");
            }

            @Override
            public void onNext(List<News> news) {
                // обновление списка новостей в UI
                newsRecycler.setVisibility(View.VISIBLE);
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
                if (bindedFragment != null && bindedFragment.get().getView() != null) {
                    newsRecycler.setVisibility(View.INVISIBLE);
                    errorText.setVisibility(View.VISIBLE);
                    errorText.setText(e.getLocalizedMessage());
                    Snackbar.make(bindedFragment.get().getView(), "Error loading news list. Swipe to reload.", Snackbar.LENGTH_LONG).show();
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
        NewsListAdapter newsListAdapter = new NewsListAdapter(new NewsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(News item) {
                // создаем новый фрагмент и згружаем туда текст новости
                Log.i(TAG, "Начало загрузка новости с ID=" + String.valueOf(item.getId()));
                openNewsDetailsFragment(item.getId());
            }
        });

        newsRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        newsRecycler.setAdapter(newsListAdapter);
        newsListAdapter.setOrUpdateDataset(news);
    }

    private void configureSwipeToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            getListOfNews(true);
        });
    }

    private void openNewsDetailsFragment(int newsID) {
        if (bindedActivity != null) {
            bindedActivity.get().openNewsDetailsFragment(newsID);
        }
    }
}
