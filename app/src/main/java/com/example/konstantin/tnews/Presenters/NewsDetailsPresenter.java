package com.example.konstantin.tnews.Presenters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.konstantin.tnews.Dagger.DependencyInjector;
import com.example.konstantin.tnews.Model.DataManager;
import com.example.konstantin.tnews.POJO.NewsDetailed.NewsDetailed;
import com.example.konstantin.tnews.R;
import com.example.konstantin.tnews.UI.NewsDetailsFragment.NewsDetailsFragment;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Konstantin on 09.11.2017.
 */

public class NewsDetailsPresenter {
    @Inject DataManager dataManager;
    @Inject Context context;

    private WeakReference<NewsDetailsFragment> bindedView;
    private final String TAG = "tNews";
    private WebView newsDetails;
    private int currentID;
    SwipeRefreshLayout swipeRefreshLayout;

    public NewsDetailsPresenter() {
        DependencyInjector.getComponent().inject(this);
    }

    // Прикрепление и открепление активити в зависимости от ЖЦ
    public void attachView(@NonNull NewsDetailsFragment view) {
        bindedView = new WeakReference<NewsDetailsFragment>(view);
        if (bindedView.get() != null) {
            newsDetails = bindedView.get().getView().findViewById(R.id.textNewsDetails);
            swipeRefreshLayout = bindedView.get().getView().findViewById(R.id.swipe_refresh_layout_details);
            configureSwipeToRefresh();
        }
    }

    public void detachView() {
        bindedView = null;
        swipeRefreshLayout = null;
        newsDetails = null;
    }

    public void setCurrentID(int currentID) {
        this.currentID = currentID;
    }

    // updateList: true - обновить с сервера, false вернуть закешированное
    public void getNewsDetails(int newsId, boolean updateNews) {
        dataManager.getNewsDetailsById(newsId, getNewsDetailsObserver(), updateNews);
    }


    private Observer<NewsDetailed> getNewsDetailsObserver() {
        // Observer для текста новости
        // Тут обработка ошибок и действия после получения данных.
        return new Observer<NewsDetailed>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "Observer<NewsDetailed> метод onSubscribe(Disposable d) вызван");
            }

            @Override
            public void onNext(NewsDetailed newsDetailed) {
                // обновление списка новостей в UI
                if (swipeRefreshLayout != null) {

                    // отображение новости ва WebView для корректной работы
                    // встречающихся в тексте ссылок
                    newsDetails.loadDataWithBaseURL(null,
                            newsDetailed.getDetails().getContent(),
                            "text/html",
                            "en_US",
                            null);

                    swipeRefreshLayout.setRefreshing(false);
                }
                // кеширование полученного списка
                dataManager.updateListDetailedCache(newsDetailed);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, e.getLocalizedMessage());
                if (bindedView != null && bindedView.get().getView() != null) {
                    Snackbar.make(bindedView.get().getView(), e.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "Observer<NewsDetailed> метод onComplete() вызван");
            }
        };
    }




    private void configureSwipeToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                // принудительное обновление текста новости
                getNewsDetails(currentID,true);
            }
        });
    }

}
