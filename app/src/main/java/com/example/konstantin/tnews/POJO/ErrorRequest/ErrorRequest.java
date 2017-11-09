package com.example.konstantin.tnews.POJO.ErrorRequest;

import com.google.gson.annotations.SerializedName;

/**
 * Сообщение о некорректном завершении запроса списка заголовков новостей к серверу api.tinkoff.ru/v1/
 *
 * Created by Konstantin on 08.11.2017.
 */

public class ErrorRequest {
    @SerializedName("resultCode")
    private String errCode;
    @SerializedName("errorMessage")
    private String errMessage;
    @SerializedName("plainMessage")
    private String plainMessage;
    @SerializedName("trackingId")
    private long trackingId;

    public String getErrCode() {
        return errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public String getPlainMessage() {
        return plainMessage;
    }

    public long getTrackingId() {
        return trackingId;
    }
}
