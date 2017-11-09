package com.example.konstantin.tnews.Model;

import android.util.Log;

import com.example.konstantin.tnews.Dagger.DependencyInjector;
import com.example.konstantin.tnews.POJO.ErrorRequest.ErrorRequest;
import com.example.konstantin.tnews.POJO.NewsDetailed.NewsDetailed;
import com.example.konstantin.tnews.POJO.NewsList.NewsList;
import com.google.gson.Gson;

import java.io.IOException;

import javax.inject.Inject;

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
    public void getNewsList() {

        Call<NewsList> callNewsList = api.getListOfNews();

        callNewsList.enqueue(new Callback<NewsList>() {
            @Override
            public void onResponse(Call<NewsList> call, Response<NewsList> response) {
                if (response.isSuccessful()) {
                    NewsList newsList = response.body();
                    Log.i(TAG, "Список заголовков новостей обновлен успешно.");
                } else handleErrorRequest(call, response); // обработка ответа с ошибкой
            }

            @Override
            public void onFailure(Call<NewsList> call, Throwable t) {
                handleOnFailure(call, t); // обработка неудачного запроса
            }
        });
    }

    public void getNewsDetailsById(int id) {
        final int newsId = id;

        Call<NewsDetailed> callNewsDetailed = api.getNewsDetailed(newsId);

        callNewsDetailed.enqueue(new Callback<NewsDetailed>() {
            @Override
            public void onResponse(Call<NewsDetailed> call, Response<NewsDetailed> response) {
                if (response.isSuccessful()) {
                    NewsDetailed newsDetailed = response.body();
                    Log.i(TAG, "Детализованные данные по новости #" + newsId + " загружены успешно");
                } else handleErrorRequest(call, response); // обработка ответа с ошибкой
            }

            @Override
            public void onFailure(Call<NewsDetailed> call, Throwable t) {
                handleOnFailure(call, t); // обработка неудачного запроса
            }
        });
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
