package com.example.konstantin.tnews.UI.MainActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.konstantin.tnews.R;
import com.example.konstantin.tnews.UI.NewsListFragment.NewsListFragment;

public class NewsActivity extends AppCompatActivity {

    private FragmentManager fm;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();
        fragment = fm.findFragmentById(R.id.fragment_container);

        // если фрагмент еще не существует - создаем новый список заголовков
        // иначе - загружаем уже подготовленный
        if (fragment == null) {
            fragment = createNewsListFragment();
        }

        fm.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .addToBackStack(fragment.getClass().getName())
                .commit();
    }

    protected Fragment createNewsListFragment() {
        return new NewsListFragment();
    }
}
