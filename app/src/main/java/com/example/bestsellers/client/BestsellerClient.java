package com.example.bestsellers.client;

import com.example.bestsellers.BuildConfig;
import com.example.bestsellers.client.api.TimesBestsellersApi;
import com.example.bestsellers.models.BestsellerListsResponse;
import com.example.bestsellers.models.BestsellerResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BestsellerClient {

    private static BestsellerClient singletonInstance;

    private TimesBestsellersApi api;


    public static synchronized BestsellerClient getInstance() {

        if (singletonInstance == null) {
            singletonInstance = new BestsellerClient();
        }

        return singletonInstance;
    }

    // can only instantiate this through getInstance()
    private BestsellerClient() {
        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nytimes.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(TimesBestsellersApi.class);
    }

    public void getBestsellerLists(final BestsellerClientCallback<BestsellerListsResponse> callback) {
        Call<BestsellerListsResponse> call = api.getBestsellerLists(BuildConfig.API_KEY);

        call.enqueue(new Callback<BestsellerListsResponse>() {
            @Override
            public void onResponse(Call<BestsellerListsResponse> call, Response<BestsellerListsResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<BestsellerListsResponse> call, Throwable t) {
                callback.onError(new BestsellerException("Error getting bestseller list", t));
            }
        });
    }

    public void getBestsellersForListAndDate(final String listName, final String publishDate, final BestsellerClientCallback<BestsellerResponse> callback) {
        Call<BestsellerResponse> call = api.getBestsellerListForNameAndDate(
                publishDate,
                listName,
                BuildConfig.API_KEY);

        call.enqueue(new Callback<BestsellerResponse>() {
            @Override
            public void onResponse(Call<BestsellerResponse> call, Response<BestsellerResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<BestsellerResponse> call, Throwable t) {
                callback.onError(new BestsellerException(String.format("Error getting list %s for date %s", listName, publishDate), t));
            }
        });
    }

    public interface BestsellerClientCallback<ResponseType> {

        void onSuccess(ResponseType response);

        void onError(BestsellerException error);

    }

    public class BestsellerException extends Exception {

        public BestsellerException(String message, Throwable cause) {
            super(message, cause);
        }

    }

}
