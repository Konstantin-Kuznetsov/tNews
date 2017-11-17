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

//        newsObservable
//                .subscribeOn(Schedulers.io()) // преобразования в io thread
//                .observeOn(AndroidSchedulers.mainThread()) // результат получаем в UI thread
//                .subscribe(sortedList -> {
//                    for (News n: sortedList) {
//                        Log.i(TAG, "UTC time:" + n.getDateOfPublication() + " Текст сообщения:" + n.getText());
//                    }
//                });

        //Observable<News> newsObservable = newsListObservable.concatMapIterable(getNewsList());

//        callNewsList.enqueue(new Callback<NewsList>() {
//            @Override
//            public void onResponse(Call<NewsList> call, Response<NewsList> response) {
//                if (response.isSuccessful()) {
//                    NewsList newsList = response.body();
//                    Log.i(TAG, "Список заголовков новостей обновлен успешно.");
//                } else handleErrorRequest(call, response); // обработка ответа с ошибкой
//            }
//
//            @Override
//            public void onFailure(Call<NewsList> call, Throwable t) {
//                handleOnFailure(call, t); // обработка неудачного запроса
//            }
//        });
    }

    public Observable<NewsDetailed> getNewsDetailsById(int id) {

        Observable<NewsDetailed> observableNewsDetailed = api.getNewsDetailed(id);

        return observableNewsDetailed;

//        callNewsDetailed.enqueue(new Callback<NewsDetailed>() {
//            @Override
//            public void onResponse(Call<NewsDetailed> call, Response<NewsDetailed> response) {
//                if (response.isSuccessful()) {
//                    NewsDetailed newsDetailed = response.body();
//                    Log.i(TAG, "Детализованные данные по новости #" + newsId + " загружены успешно");
//                } else handleErrorRequest(call, response); // обработка ответа с ошибкой
//            }
//
//            @Override
//            public void onFailure(Call<NewsDetailed> call, Throwable t) {
//                handleOnFailure(call, t); // обработка неудачного запроса
//            }
//        });
    }


    // обрабатываем response.errorBody(), формирую корректную формулировку сообщения об ощибке
    private void handleErrorRequest(Call call, Response response) {
        try {
            if (response.errorBody() != null) {
                ErrorRequest errorRequest = gson.fromJson(response.errorBody().string(), ErrorRequest.class);
                Log.e(TAG, "Сервер возвратил ответ с ошибкой " + errorRequest.getErrCode()
                        + '\n' + errorRequest.getPlainMessage()
                        + '\n' + "на запрос " + call.toString());
            }
        } catch (IOException e) {
            Log.e(TAG, "Сервер возвратил ответ с ошибкой.");
            e.printStackTrace();
        }
    }

    // обработка неудачного запроса к серверу
    private void handleOnFailure(Call call, Throwable t) {
        Log.e(TAG, "Ошибка обновления данных с https://api.tinkoff.ru/v1/"
                + '\n' + t.getMessage()
                + '\n' + "Запрос: " + call.toString());
    }
}
