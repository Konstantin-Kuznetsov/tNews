package com.example.konstantin.tnews.Model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.konstantin.tnews.Dagger.DependencyInjector;
import com.example.konstantin.tnews.POJO.NewsList.News;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Konstantin on 09.11.2017.
 */

public class DataManager {

    @Inject RESTDataProvider restDataProvider;
    @Inject CacheHelper cacheHelper;
    @Inject Context context;

    private final String TAG = "tNews";

    public DataManager() {
        DependencyInjector.getComponent().inject(this);
    }

    public void getNewsList(Observer<List<News>> newsListObserver, boolean updateList) {

        if (updateList) {
            // обновляем список
            subscribeToNewList(newsListObserver);
        } else {
            // возвращаем из кэша, если там есть что возвращать
            // если кэш пуст - грузим из сети
            if (cacheHelper.isListCached()) {
                subscribeToCachedList(newsListObserver);
            } else {
                subscribeToNewList(newsListObserver);
            }
        }
    }

    private void subscribeToNewList(Observer<List<News>> observer) {
        // Retrofit передает сразу объект Observable, на который мы подписываемся
        // переданным из презентера observer
        restDataProvider.getNewsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        Log.i(TAG, "Загрузка через API tinkoff нового списка заголовков новостей");
    }

    private void subscribeToCachedList(Observer<List<News>> observer) {
        Observable
                .just(cacheHelper.getCachedList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        Log.i(TAG, "Загрузка закешированного списка заголовков новостей");
    }

    public void getNewsDetailsById(int id) {
        //TODO проверка в кэше, если нет- сетевой запрос
        restDataProvider.getNewsDetailsById(id);
    }

    // кеширование полученного списка новостей
    public void updateListNewsCache(List<News> news) {
        cacheHelper.putNewsList(news);
    }

    private boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
