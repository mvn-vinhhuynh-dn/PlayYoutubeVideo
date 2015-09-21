package com.example.asiantech.playyoutubevideo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asiantech.playyoutubevideo.R;
import com.example.asiantech.playyoutubevideo.object.Video;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2015 AsianTech inc.
 * Created by VinhHlb on 9/17/15.
 */
public class RelatedVideoAdapter extends RecyclerView.Adapter<RelatedVideoAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private ImageLoader mImageLoader;
    private DisplayImageOptions mOptions;
    private List<Video.ItemsEntity> mItems = new ArrayList<>();
    private OnPlayYtbViDeo mListener;


    public RelatedVideoAdapter(Context context, List<Video.ItemsEntity> items, OnPlayYtbViDeo onPlayYtbViDeo) {
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_video_related, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTvTitle.setText(mItems.get(position).getSnippet().getTitle());
        holder.mtvDes.setText(mItems.get(position).getSnippet().getDescription());
        mImageLoader.displayImage(mItems.get(position).getSnippet().getThumbnails().getMedium().getUrl(), holder.mImgThumbs);
        holder.mLnParent.setTag(position);
        holder.mLnParent.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mLnParent:
                int pos = (Integer) v.getTag();
                mListener.OnPlayYtbViDeoOutSize(mItems.get(pos).getId().getVideoId(), pos, mItems.get(pos).getSnippet().getThumbnails().getHigh().getUrl());
                break;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvTitle;
        private ImageView mImgThumbs;
        private LinearLayout mLnParent;
        private TextView mtvDes;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mImgThumbs = (ImageView) itemView.findViewById(R.id.imgThumbs);
            mLnParent = (LinearLayout) itemView.findViewById(R.id.mLnParent);
            mtvDes = (TextView) itemView.findViewById(R.id.tvDesCriptions);
        }
    }

    public interface OnPlayYtbViDeo {
        void OnPlayYtbViDeoOutSize(String videoId, int position, String urlImageThumb);
    }
}