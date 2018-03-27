package com.example.bestsellers.client.api;

import com.example.bestsellers.models.BestsellerListsResponse;
import com.example.bestsellers.models.BestsellerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TimesBestsellersApi {

    @GET("/svc/books/v3/lists/names.json")
    Call<BestsellerListsResponse> getBestsellerLists(@Query("api-key") String apiKey);

    @GET("/svc/books/v3/lists/{publishDate}/{listName}.json")
    Call<BestsellerResponse> getBestsellerListForNameAndDate(@Path("publishDate") String publishDate,
                                                             @Path("listName") String listName,
                                                             @Query("api-key") String apiKey);

}
