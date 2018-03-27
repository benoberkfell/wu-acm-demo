package com.example.bestsellers.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BestsellerListsResponse {

    /*
     *
     * How are these private, but GSON is able to create objects with these values filled, with
     * no setter method?
     *
     * GSON uses Java reflection to directly look for fields matching the JSON keys.
     */

    @SerializedName("status")
    private String status;

    @SerializedName("copyright")
    private String copyright;

    @SerializedName("num_results")
    private int numResults;

    @SerializedName("results")
    private List<BestsellerList> results;

    public String getStatus() {
        return status;
    }

    public String getCopyright() {
        return copyright;
    }

    public int getNumResults() {
        return numResults;
    }

    public List<BestsellerList> getResults() {
        return results;
    }


}
