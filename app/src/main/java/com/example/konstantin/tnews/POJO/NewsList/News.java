package com.example.konstantin.tnews.POJO.NewsList;

import com.google.gson.annotations.SerializedName;

/**
 * Отдельная новость с заголовком.
 *
 * Created by Konstantin on 08.11.2017.
 */

public class News {
    @SerializedName("id")
    private int id; // id новости
    @SerializedName("name")
    private String name; // имя новости ENG
    @SerializedName("text")
    private String text; // заголовок новости
    @SerializedName("publicationDate")
    private PublicationDate publicationDate; // дата публикации (UTC - ?)
    @SerializedName("bankInfoTypeId")
    private Integer bankInfoTypeId; // тип новости. везде в json - 2

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
}
