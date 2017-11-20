package com.onyx.flickrview.webservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.onyx.flickrview.data.FlickrApiResponse;
import com.onyx.flickrview.data.Image;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;


public class FlickrService implements IFlickrService {

    @Override
    public void getImages(final FlickrServiceCallback<FlickrApiResponse> callback) {
        // Call flicker endpoint
        Timber.d("onyx:Calling endpoint");
        Call<String> imagesCall = new FlickrClient().getService().getImages();
        imagesCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Timber.d("onyx:respones, %s", response.body());
                if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {

                    callback.onLoaded(parseResponse(response.body()));
                } else {
                    Timber.d("Onyx:Something went wrong");
                    callback.onError("Onyx:Something went wrong");
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Timber.d("onyx:retrofit error, %s", t.getMessage());
                callback.onError("Not really your fault, probably poor internet connection");
            }
        });
    }


    public FlickrApiResponse parseResponse(String feedData) {

        int lastIndex = feedData.length() - 1;
        StringBuffer formattedJsonString = new StringBuffer(feedData);

        formattedJsonString.replace(lastIndex, lastIndex, "");
        formattedJsonString.replace(0, 15, "");

        String jsonString = formattedJsonString.toString();


        JSONObject json = null;
        try {
            json = new JSONObject(jsonString);
        } catch (JSONException e) {
            Timber.d("Error parsing response: %s", e.getMessage());
        }
        JSONArray imagesArray;
        try {
            imagesArray = json.getJSONArray("items");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        Gson gson = new GsonBuilder().create();
        Type listType = new TypeToken<List<Image>>() {
        }.getType();
        ArrayList<Image> imagesJson = gson.fromJson(imagesArray.toString(), listType);

        int numberOfImages = imagesJson.size();
        Image[] imageItems = new Image[numberOfImages];

        int index = 0;
        for (Image image : imagesJson) {
            imageItems[index] = image;
            index++;
        }
        FlickrApiResponse response = new FlickrApiResponse();
        response.setImages(imageItems);

        return response;
    }
}
