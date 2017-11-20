package com.onyx.flickrview.webservice;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FlickrClient {
    private FlickrEndpoint service;

    public FlickrClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.flickr.com/services/")
                .client(new OkHttpClient())
                .addConverterFactory(new StringConverterFactory())

                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient().create()))
                .build();

        service = retrofit.create(FlickrEndpoint.class);
    }

    public FlickrEndpoint getService() {
        return service;
    }

}
