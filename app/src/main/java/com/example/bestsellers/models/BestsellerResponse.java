package com.example.bestsellers.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BestsellerResponse {

    @SerializedName("results")
    private BestsellerInfo results;

    public String getDisplayName() {
        return results.displayName;
    }

    public String getPublishedDate() {
        return results.publishedDate;
    }

    public List<Book> getBooks() {
        return results.books;
    }

    private class BestsellerInfo {

        @SerializedName("display_name")
        private String displayName;

        @SerializedName("published_date")
        private String publishedDate;

        @SerializedName("books")
        private List<Book> books;

    }

}
