package com.example.konstantin.tnews.UI.NewsDetailsFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.konstantin.tnews.Dagger.DependencyInjector;
import com.example.konstantin.tnews.Presenters.NewsDetailsPresenter;
import com.example.konstantin.tnews.R;

import javax.inject.Inject;

/**
 *  Фрагмент с текстом новости
 *
 * Created by Konstantin on 09.11.2017.
 */

public class NewsDetailsFragment extends Fragment {

    @Inject NewsDetailsPresenter presenter;

    private int currentID;
    private final String NEWS_ID = "news_id";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            // создающемуся фрагменту передается ID новости для загрузки
            currentID = bundle.getInt(NEWS_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_details, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DependencyInjector.getComponent().inject(this);

        presenter.attachView(this);
        presenter.setCurrentID(currentID); // передача презентеру ID новости
        presenter.getNewsDetails(currentID,false); // вернуть закешированное, если есть
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.attachView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.detachView();
    }
}
