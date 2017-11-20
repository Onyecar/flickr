package com.onyx.flickrview.webservice;

import retrofit2.Call;
import retrofit2.http.GET;



public interface FlickrEndpoint {

    @GET("feeds/photos_public.gne?format=json")
    Call<String> getImages();
    //TODO: break into query params later
}
