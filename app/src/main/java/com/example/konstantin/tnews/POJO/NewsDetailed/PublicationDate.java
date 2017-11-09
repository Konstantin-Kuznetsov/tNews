package com.example.konstantin.tnews.POJO.NewsDetailed;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Konstantin on 08.11.2017.
 */

public class PublicationDate {
    @SerializedName("milliseconds")
    private long milliseconds;

    public long getDateOfPublication() {
        return milliseconds;
    }
}
