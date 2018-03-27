package com.example.bestsellers.models;

import com.google.gson.annotations.SerializedName;

public class BestsellerList {

    @SerializedName("list_name")
    private String name;

    @SerializedName("list_name_encoded")
    private String encodedName;

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("oldest_published_date")
    String oldestPublishedDate;

    @SerializedName("newest_published_date")
    String newestPublishedDate;

    @SerializedName("updated")
    String updated;

    public String getName() {
        return name;
    }

    public String getEncodedName() {
        return encodedName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getOldestPublishedDate() {
        return oldestPublishedDate;
    }

    public String getNewestPublishedDate() {
        return newestPublishedDate;
    }

    public String getUpdated() {
        return updated;
    }


}
