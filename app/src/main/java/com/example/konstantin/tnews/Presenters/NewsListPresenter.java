package com.example.konstantin.tnews.Presenters;

import android.content.Context;
import android.support.annotation.NonNull;
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

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static io.reactivex.Single.just;

/**
 * Created by Konstantin on 09.11.2017.
 */

public class NewsListPresenter {
    @Inject DataManager dataManager;
    @Inject Context context;

    private WeakReference<NewsListFragment> bindedView;
    private final String TAG = "tNews";
    RecyclerView newsRecycler;

    public NewsListPresenter() {
        DependencyInjector.getComponent().inject(this);
    }

    // Прикрепление и открепление активити в зависимости от ЖЦ
    public void attachView(@NonNull NewsListFragment view) {
        bindedView = new WeakReference<NewsListFragment>(view);
        if (bindedView.get() != null) {
            newsRecycler = bindedView.get().getView().findViewById(R.id.newsListRecycler);
        }

    }

    public void detachView() {
        bindedView = null;
    }

    public void getListOfNews() {
        dataManager.getNewsList(getListObserver()); // запрос списка новостей
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
                if (newsRecycler != null) {
                    configureRecyclerView(news);
                }

                // кеширование полученного списка
                dataManager.updateListNewsCache(news);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "Observer<List<News>> метод onComplete() вызван");
            }
        };
    }

    public void configureRecyclerView(List<News> news) {
        NewsListAdapter newsListAdapter = new NewsListAdapter();
        newsRecycler.setAdapter(newsListAdapter);
        newsRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        newsListAdapter.setOrUpdateDataset(news);
    }

}
