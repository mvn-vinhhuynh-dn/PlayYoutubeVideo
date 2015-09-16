package com.example.asiantech.playyoutubevideo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asiantech.playyoutubevideo.R;
import com.example.asiantech.playyoutubevideo.object.Items;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anhtt on 08/09/2015.
 */
public class CustomVideoHome extends RecyclerView.Adapter implements View.OnClickListener {
    private Context mContext;
    private ImageLoader mImageLoader;
    private DisplayImageOptions mOptions;
    private List<Items.ItemsEntity> mItems = new ArrayList<>();
    private String mKeys[];
    private OnPlayYtbViDeo mListener;

    public CustomVideoHome(Context context, List<Items.ItemsEntity> items, OnPlayYtbViDeo onPlayYtbViDeo) {
        this.mContext = context;
        this.mItems = items;
        mListener = onPlayYtbViDeo;
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        mOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VideoViewHolder) {
            Items.ItemsEntity items = mItems.get(position);
            ((VideoViewHolder) holder).tvTitle.setText(items.getSnippet().getTitle());
            mImageLoader.displayImage(items.getSnippet().getThumbnails().getMedium().getUrl(),
                    ((VideoViewHolder) holder).imgAvatar, mOptions);
            ((VideoViewHolder) holder).imgAvatar.setOnClickListener(this);
            ((VideoViewHolder) holder).imgAvatar.setTag(position);
//            Log.d("vinh1111","\n" + items.getSnippet().getResourceId().getVideoId());
        }

    }

    @Override
    public int getItemCount() {
        return mItems.size();

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgVideoView:
                int position = (Integer) v.getTag();
                mListener.OnPlayVideo(mItems.get(position).getSnippet().getResourceId().getVideoId(), position, mItems.get(position).getSnippet().getThumbnails().getHigh().getUrl());
                break;
        }
    }

    private class VideoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private ImageView imgAvatar;

        public VideoViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tvTitelVideo);
            imgAvatar = (ImageView) view.findViewById(R.id.imgVideoView);

        }
    }

    private int showHeader() {
        int count = mItems.size();
        int i = 0;
        while (count < 0) {
            if (count % 25 == 0) {
                count -= 25;
                return 1;
            }
        }
        return 0;
    }

    public interface OnPlayYtbViDeo {
        void OnPlayVideo(String videoId, int position, String urlImageThumb);
    }
}