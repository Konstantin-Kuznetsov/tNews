package com.example.konstantin.tnews.POJO.NewsDetailed;

import com.google.gson.annotations.SerializedName;

/**
 * Детализованная информация по отдельной новости
 *
 * Created by Konstantin on 08.11.2017.
 */

public class NewsDetailed {
    @SerializedName("resultCode")
    private String resultCode; // ОК

    @SerializedName("payload")
    private Details details; // вся необходимая информация для отображения в UI

    @SerializedName("trackingId")
    private long trackingId; // ?? служебная информация

    public String getResultCode() {
        return resultCode;
    }

    public Details getDetails() {
        return details;
    }

    public long getTrackingId() {
        return trackingId;
    }
}
