package com.example.konstantin.tnews.POJO.NewsDetailed;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Konstantin on 08.11.2017.
 */

public class Title {
    @SerializedName("id")
    private int id; // id новости
    @SerializedName("name")
    private String name; // имя новости ENG
    @SerializedName("text")
    private String text; // заголовок новости
    @SerializedName("publicationDate")
    private PublicationDate publicationDate; // дата публикации (UTC - ?) сортировка по этому полю
    @SerializedName("bankInfoTypeId")
    private int bankInfoTypeId; // тип новости. везде в json - 2

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public PublicationDate getPublicationDate() {
        return publicationDate;
    }

    public int getBankInfoTypeId() {
        return bankInfoTypeId;
    }
}
