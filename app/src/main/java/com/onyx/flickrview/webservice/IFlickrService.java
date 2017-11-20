package com.onyx.flickrview.webservice;

import com.onyx.flickrview.data.FlickrApiResponse;

/**
 * Created by Morph-Deji on 19-Nov-17.
 */

public interface IFlickrService {
    interface FlickrServiceCallback<T> {

        void onLoaded(T data);
        void onError(String message);
    }

    void getImages(FlickrServiceCallback<FlickrApiResponse> callback);
}
