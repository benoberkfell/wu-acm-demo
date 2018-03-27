package com.example.bestsellers.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Book {

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("author")
    private String author;

    @SerializedName("rank")
    private int rank;

    @SerializedName("weeks_on_list")
    private int weeksOnList;

    @SerializedName("amazon_product_url")
    private String amazonUrl;


    public int getRank() {
        return rank;
    }

    public int getWeeksOnList() {
        return weeksOnList;
    }

    public String getAmazonUrl() {
        return amazonUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }
}
