package com.onyx.flickrview.viewimages;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.onyx.flickrview.data.FlickrApiResponse;
import com.onyx.flickrview.data.Image;
//import com.onyx.flickrview.service.FlickrService;
import com.onyx.flickrview.webservice.IFlickrService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class ImagesPresenter implements ImagesContract.ActionsListener{

    private static final String TAG = ImagesPresenter.class.getSimpleName();

    private final ImagesContract.View mImagesView;

    private IFlickrService mWebService;

    public ImagesPresenter(@NonNull ImagesContract.View imagesView, IFlickrService service) {
        mImagesView = checkNotNull(imagesView, "imagesView cannot be null!");
        mWebService = checkNotNull(service, "data source cannot be empty");


    }

    @Override
    public void loadImages(boolean forceUpdate) {
        mImagesView.setProgressIndicator(true);
        mWebService.getImages(new IFlickrService.FlickrServiceCallback<FlickrApiResponse>() {
            @Override
            public void onLoaded(FlickrApiResponse data) {
                mImagesView.setProgressIndicator(false);
                if (data != null) {
                    if(data.getImages() != null) {
                        mImagesView.showImages(data.getImages());
                    }else {
                        mImagesView.showError("Error");
                    }
                }else {
                    mImagesView.showError("Error");
                }
            }

            @Override
            public void onError(String message) {
                mImagesView.showError(message);
            }
        });

//        if (!forceUpdate) {
//            return;
//        }
        //new FetchImageTask().execute();
    }

//
//    public class FetchImageTask extends AsyncTask<String, Void, Image[]> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            Log.d(TAG, "In FetchImageTask onPreExecute");
//            mImagesView.setProgressIndicator(true);
//        }
//
//        @Override
//        protected Image[] doInBackground(String... params) {
//            URL imgRequestUrl = FlickrService.buildUrl();
//            Log.d(TAG, "FetchImageTask doInBackground. URL is "+ imgRequestUrl);
//            try {
//                String excJsonResponse = FlickrService.getResponse(imgRequestUrl);
//                Image[] imageData = formatJson(excJsonResponse);
//                for(Image image: imageData)
//                    Log.d(TAG, image.toString());
//
//
//                return imageData;
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(Image[] imageData) {
//            Log.d(TAG, "In onPostExecute" );
//            mImagesView.setProgressIndicator(false);
//            if (imageData != null) {
//                String[] imageUrls = new String[imageData.length];
//                mImagesView.getImages(imageData);
//            }
//        }
//    }

    public Image[] formatJson (String jsonString) throws JSONException {

        int lastIndex = jsonString.length()-1;
        StringBuffer formattedJsonString = new StringBuffer(jsonString);

        formattedJsonString.replace(lastIndex,lastIndex,"");
        formattedJsonString.replace(0,15,"");

        jsonString = formattedJsonString.toString();


        JSONObject json = new JSONObject(jsonString);
        JSONArray imagesArray;
        try {
            imagesArray = json.getJSONArray("items");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        Gson gson = new GsonBuilder().create();
        Type listType = new TypeToken<List<Image>>() {}.getType();
        ArrayList<Image> imagesJson = gson.fromJson(imagesArray.toString(), listType);

        int numberOfImages = imagesJson.size();
        Image[] imageItems = new Image[numberOfImages];

        int index = 0;
        for(Image image: imagesJson){
            imageItems[index]=image;
            index++;
        }

        return imageItems;
    }
}
