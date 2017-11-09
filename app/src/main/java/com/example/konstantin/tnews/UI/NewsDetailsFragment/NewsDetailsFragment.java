package com.example.konstantin.tnews.UI.NewsDetailsFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.konstantin.tnews.Dagger.DependencyInjector;
import com.example.konstantin.tnews.Model.DataManager;
import com.example.konstantin.tnews.Presenters.NewsDetailsPresenter;
import com.example.konstantin.tnews.R;

import javax.inject.Inject;

/**
 * Created by Konstantin on 09.11.2017.
 */

public class NewsDetailsFragment extends Fragment {

    @Inject NewsDetailsPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    @Override
    public void onResume() {
        super.onResume();
        //presenter.attachView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        //presenter.detachView();
    }
}
