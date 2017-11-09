package com.example.konstantin.tnews.POJO.NewsDetailed;

import com.google.gson.annotations.SerializedName;

/**
 * Подробная информация по новости (дата создания/редактирования, текст новости и т.д)
 *
 * Created by Konstantin on 08.11.2017.
 */

public class Details {
    @SerializedName("title")
    private Title title;
    @SerializedName("creationDate")
    private CreationDate creationDate;
    @SerializedName("lastModificationDate")
    private LastModificationDate lastModificationDate;
    @SerializedName("content")
    private String content; // содержание текста статьи
    @SerializedName("bankInfoTypeId")
    private int bankInfoTypeId; // везде 2
    @SerializedName("typeId")
    private String typeId; // usual

    public Title getTitle() {
        return title;
    }

    public CreationDate getCreationDate() {
        return creationDate;
    }

    public LastModificationDate getLastModificationDate() {
        return lastModificationDate;
    }

    public String getContent() {
        return content;
    }

    public int getBankInfoTypeId() {
        return bankInfoTypeId;
    }

    public String getTypeId() {
        return typeId;
    }
}
