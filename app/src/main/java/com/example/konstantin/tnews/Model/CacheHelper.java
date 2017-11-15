package com.example.konstantin.tnews.Model;

import android.util.LruCache;

import com.example.konstantin.tnews.POJO.NewsList.News;

import java.util.List;

/**
 * Created by Konstantin on 09.11.2017.
 */

public class CacheHelper {
    private final String TAG = "tNews";
    private final String NEWS_LIST = "news_list";
    private LruCache<String, List<News>> newsListCached;
    private LruCache<Long, News> newsDetailsCached;

    public CacheHelper() {
        newsListCached = new LruCache<String, List<News>>(1);
        newsDetailsCached = new LruCache<Long, News>(15); // последние 15 открытых новостей
    }

    // true- есть закешированный список, false- пусто
    public boolean isListCached() {
        return newsListCached.get(NEWS_LIST) != null;
    }

    public void putNewsList(List<News> list) {
        newsListCached.put(NEWS_LIST, list);
    }

    public List<News> getCachedList() {
        return newsListCached.get(NEWS_LIST);
    }

    // true- есть закешированная новость, false- запрашивается впервые, либо запрашивалась давно
    public boolean isNewsDetailsCached(long dateOfPublication) {
        return newsDetailsCached.get(dateOfPublication) != null;
    }

    public void putNews(News news) {
        newsDetailsCached.put(news.getDateOfPublication(), news);
    }

    public News getNewsDetails(long dateOfPublication) {
        return newsDetailsCached.get(dateOfPublication);
    }

}
