package com.example.konstantin.tnews.Model;

import android.util.Log;

import com.example.konstantin.tnews.Dagger.DependencyInjector;
import com.example.konstantin.tnews.POJO.ErrorRequest.ErrorRequest;
import com.example.konstantin.tnews.POJO.NewsDetailed.NewsDetailed;
import com.example.konstantin.tnews.POJO.NewsList.News;
import com.example.konstantin.tnews.POJO.NewsList.NewsList;
import com.google.gson.Gson;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Konstantin on 09.11.2017.
 */

public class RESTDataProvider {

    @Inject Gson gson;
    @Inject tNewsApi.tinkoffNewsInterface api; // api новостей tinkoff

    private final String TAG = "tNews";

    public RESTDataProvider() {
        // инъекция зависимостей с помощью Dagger2
        DependencyInjector.getComponent().inject(this);
    }

    // запрос списка говостей(без дополнительных параметров)
    public Observable<List<News>> getNewsList() {

        Observable<NewsList> newsListObservable = api.getListOfNews();

        // преобразуем поток из одного элемента Observable<NewsList>
        // в поток Observable<List<News>>, с объектом на каждую новость.
        // Сортировка - обратная, по полю DateOfPublication.
        Observable<List<News>> newsObservable = newsListObservable
                .concatMapIterable(NewsList::getNewsList)
                .toSortedList((news1, news2) -> (-1)*(Long.valueOf(news1.getDateOfPublication())).compareTo(news2.getDateOfPublication()))
                .toObservable();

        return newsObservable;

    }

    public Observable<NewsDetailed> getNewsDetailsById(int id) {

        Observable<NewsDetailed> observableNewsDetailed = api.getNewsDetailed(id);

        return observableNewsDetailed;
    }
}
