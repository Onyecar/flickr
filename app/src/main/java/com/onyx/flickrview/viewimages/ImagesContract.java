package com.onyx.flickrview.viewimages;

import com.onyx.flickrview.data.Image;

/**
 * Created by onyekaanene on 17/11/2017.
 */

public interface ImagesContract {
    interface View {

        void setProgressIndicator(boolean active);

        void getImages(Image[] images);
        void showImages(Image[] images);

        void showError(String error);
//        void getImages(List<Image> images);

    }
    interface ActionsListener {

        void loadImages(boolean forceUpdate);
    }
}
