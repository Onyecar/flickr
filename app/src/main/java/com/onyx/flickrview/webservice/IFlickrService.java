package com.onyx.flickrview.webservice;

import com.onyx.flickrview.data.FlickrApiResponse;


public interface IFlickrService {
    interface FlickrServiceCallback<T> {

        void onLoaded(T data);
        void onError(String message);
    }

    void getImages(FlickrServiceCallback<FlickrApiResponse> callback);
}
