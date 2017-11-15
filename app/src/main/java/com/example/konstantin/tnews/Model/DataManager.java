package com.example.konstantin.tnews.Model;

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

    private final String TAG = "tNews";

    public DataManager() {
        DependencyInjector.getComponent().inject(this);
    }

    public void getNewsList(Observer<List<News>> newsListObserver) {
        if (cacheHelper.isListCached()) {
            // возвращаем из кэша, если там есть что возвращать
            Observable
                    .just(cacheHelper.getCachedList())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(newsListObserver);
            Log.i(TAG, "Загрузка закешированного списка заголовков новостей");
        } else {
            // Retrofit передает сразу объект Observable, на который мы подписываемся
            // переданным из презентера observer
            restDataProvider.getNewsList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(newsListObserver);
            Log.i(TAG, "Загрузка через API tinkoff нового списка заголовков новостей");
        }
    }

    // принудительное обновление
    public void updateNewsList(Observer<List<News>> newsListObserver) {
        restDataProvider.getNewsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsListObserver);
        Log.i(TAG, "Загрузка через API tinkoff нового списка заголовков новостей");
    }

    public void getNewsDetailsById(int id) {
        //TODO проверка в кэше, если нет- сетевой запрос
        restDataProvider.getNewsDetailsById(id);
    }

    // кеширование полученного списка новостей
    public void updateListNewsCache(List<News> news) {
        cacheHelper.putNewsList(news);
    }
}
