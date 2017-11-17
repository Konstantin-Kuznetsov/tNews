package com.example.konstantin.tnews.Model;

import android.util.LruCache;

import com.example.konstantin.tnews.POJO.NewsDetailed.NewsDetailed;
import com.example.konstantin.tnews.POJO.NewsList.News;

import java.util.List;

/**
 * Created by Konstantin on 09.11.2017.
 */

public class CacheHelper {

    private final String NEWS_LIST = "news_list";
    private LruCache<String, List<News>> newsListCached;
    private LruCache<Integer, NewsDetailed> newsDetailsCached;

    public CacheHelper() {
        newsListCached = new LruCache<String, List<News>>(1);
        newsDetailsCached = new LruCache<Integer, NewsDetailed>(5); // последние 15 открытых новостей
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

    // true- есть закешированная новость, false- запрашивается впервые
    public boolean isNewsDetailsCached(int newsId) {
        return newsDetailsCached.get(newsId) != null;
    }

    public void putNewsDetails(NewsDetailed newsDetailed) {
        newsDetailsCached.put(newsDetailed.getDetails().getTitle().getId(), newsDetailed);
    }

    public NewsDetailed getCachedDetails(int newsId) {
        return newsDetailsCached.get(newsId);
    }

}
