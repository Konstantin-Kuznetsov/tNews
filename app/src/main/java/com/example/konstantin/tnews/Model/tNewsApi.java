package com.example.konstantin.tnews.Model;

import com.example.konstantin.tnews.POJO.NewsDetailed.NewsDetailed;
import com.example.konstantin.tnews.POJO.NewsList.NewsList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Интерфейс состоит всего из двух методов:
 *      - загрузить список новостей с заголовками
 *      - загрузить текст новости по ID
 *
 *  Ответ сервера заворачивается в Observable
 *
 * Created by Konstantin on 08.11.2017.
 */

public class tNewsApi {
    public interface tinkoffNewsInterface {
        @GET("news") // запрос списка заголовков новостей
        Observable<NewsList> getListOfNews();

        @GET("news_content") // запрос содержимого новости по ее ID
        Observable<NewsDetailed> getNewsDetailed(
                @Query("id") int id
        );
    }
}
