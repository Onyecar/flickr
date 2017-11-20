package com.onyx.flickrview.viewimages;

import com.onyx.flickrview.data.Image;

public interface ImagesContract {
    interface View {

        void setProgressIndicator(boolean active);

        void showImages(Image[] images);

        void showError(String error);
//        void getImages(List<Image> images);

    }
    interface ActionsListener {

        void loadImages(boolean forceUpdate);
    }
}
