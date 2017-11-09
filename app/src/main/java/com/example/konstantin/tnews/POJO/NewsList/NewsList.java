package com.example.konstantin.tnews.POJO.NewsList;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Список новостей с заголовками(без текста самой новости).
 *
 * Created by Konstantin on 08.11.2017.
 */

public class NewsList {
    @SerializedName("resultCode")
    private String resultCode; // ОК
    @SerializedName("payload")
    private ArrayList<News> newsList = null; // массик новостей с заголовками
    @SerializedName("trackingId")
    private long trackingId; // ?? служебная информация

    public String getResultCode() {
        return resultCode;
    }

    public ArrayList<News> getNewsList() {
        return newsList;
    }

    public long getTrackingId() {
        return trackingId;
    }
}
