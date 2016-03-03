package com.example.nikhiljoshi.naakh.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nikhiljoshi on 3/3/16.
 */
public class ServiceGenerator {

    public static final String API_BASE_URL = "https://naakh.herokuapp.com/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static <S> S createService(Class<S> serviceClass) {
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build()).build();

        return retrofit.create(serviceClass);
    }
}
