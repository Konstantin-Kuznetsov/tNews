package com.example.konstantin.tnews.UI.MainActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.konstantin.tnews.Dagger.DependencyInjector;
import com.example.konstantin.tnews.Presenters.NewsListPresenter;
import com.example.konstantin.tnews.R;
import com.example.konstantin.tnews.UI.NewsDetailsFragment.NewsDetailsFragment;
import com.example.konstantin.tnews.UI.NewsListFragment.NewsListFragment;

import javax.inject.Inject;

public class NewsActivity extends AppCompatActivity {

    @Inject NewsListPresenter presenter;

    private final String TAG = "tNews";
    private final String NEWS_ID = "news_id";

    private FragmentManager fm;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DependencyInjector.getComponent().inject(this);

        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();
        fragment = fm.findFragmentById(R.id.fragment_container);

        // если фрагмент еще не существует - создаем новый список заголовков
        // иначе - загружаем уже подготовленный
        if (fragment == null) {
            fragment = createNewsListFragment();

            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attachActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.detachActivity();
    }

    public void openNewsDetailsFragment(int newsID) {
        Fragment fragment = createNewsDetailsFragment(newsID);
        fm.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(fragment.getClass().getName())
                .commit();
    }

    protected Fragment createNewsListFragment() {
        return new NewsListFragment();
    }

    protected Fragment createNewsDetailsFragment(int newsID) {
        Fragment fragment =  new NewsDetailsFragment();
        fragment.setArguments(getBundle(newsID));
        return fragment;
    }

    private Bundle getBundle(int newsID) {
        Bundle bundle = new Bundle();
        bundle.putInt(NEWS_ID, newsID);
        return bundle;
    }
}
