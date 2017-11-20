package com.onyx.flickrview.data;

import android.support.annotation.Nullable;
import com.google.gson.annotations.SerializedName;
import com.google.common.base.Objects;

import org.json.JSONObject;


/**
 * Created by onyekaanene on 17/11/2017.
 */

public class Image {
    @Nullable
    @SerializedName("title")
    private final String mTitle;
    @Nullable
    @SerializedName("description")
    private final String mDescription;
    @Nullable
    @SerializedName("media")
    private final ImageUrl mMediaUrl;

    public Image(@Nullable String title, @Nullable String description,
                 @Nullable ImageUrl mediaUrl) {
        mTitle = title;
        mDescription = description;
        mMediaUrl = mediaUrl;
    }

    @Nullable
    public String getmTitle() {
        return mTitle;
    }

    @Nullable
    public String getmDescription() {
        return mDescription;
    }

    @Nullable
    public ImageUrl getmMediaUrl() {
        return mMediaUrl;
    }


    public boolean isEmpty() {
        return (mTitle == null || "".equals(mTitle)) &&
                (mDescription == null || "".equals(mDescription));
    }
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Image image = (Image) object;
        return Objects.equal(mTitle, image.mTitle) &&
                Objects.equal(mDescription, image.mDescription) &&
                Objects.equal(mMediaUrl, image.mMediaUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mTitle, mDescription, mMediaUrl);
    }
    @Override
    public String toString(){
        return "{\n" +
                "\"title\": \""+mTitle+"\"\n"+
                "\"description\": \""+mDescription+"\"\n"+
                "\"mediaUrl\": \""+mMediaUrl+"\"\n"+
                "\"imageUrl\": \""+mMediaUrl.getmImageUrl()+"\"\n"+
                "}";
    }
    public static class ImageUrl{
        @SerializedName("m")
        private String mImageUrl;

        public String getmImageUrl() {
            return mImageUrl;
        }
        public ImageUrl(String imageUrl){
            mImageUrl=imageUrl;
        }
    }
}
