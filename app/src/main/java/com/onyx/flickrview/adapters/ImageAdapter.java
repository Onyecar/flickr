package com.onyx.flickrview.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.bumptech.glide.Glide;

import com.onyx.flickrview.data.Image;
import com.onyx.flickrview.R;

import static com.google.common.base.Preconditions.checkNotNull;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private final static String TAG = ImageAdapter.class.getSimpleName();
    private Image[] mImages;
    Context mContext;

    public ImageAdapter(Context context) {
        mContext = context;
        Log.d(TAG, "Called image adapter");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View imageView = inflater.inflate(R.layout.item_image, parent, false);

        return new ViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        final Image image = mImages[position];

        Glide.with(mContext).load(image.getmMediaUrl().getmImageUrl()).into(viewHolder.mImageView);
    }

    public void replaceData(Image[] images) {
        setList(images);
        notifyDataSetChanged();
    }

    private void setList(Image[] images) {
        mImages = checkNotNull(images);
    }

    @Override
    public int getItemCount() {
        if (null == mImages) return 0;
        return mImages.length;
    }

    public Image getItem(int position) {
        return mImages[position];
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public Image image;
        public final ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
