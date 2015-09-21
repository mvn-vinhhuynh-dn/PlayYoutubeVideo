package com.example.asiantech.playyoutubevideo.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.example.asiantech.playyoutubevideo.R;
import com.example.asiantech.playyoutubevideo.adapter.CustomVideoHome;
import com.example.asiantech.playyoutubevideo.adapter.RelatedVideoAdapter;
import com.example.asiantech.playyoutubevideo.core.Callback;
import com.example.asiantech.playyoutubevideo.core.api.AuthApi;
import com.example.asiantech.playyoutubevideo.mediaPlayerCustomView.VideoControllerView;
import com.example.asiantech.playyoutubevideo.object.Items;
import com.example.asiantech.playyoutubevideo.object.Video;
import com.github.pedrovgs.DraggableListener;
import com.github.pedrovgs.DraggableView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;


/**
 * Created by anhtt on 04/09/2015.
 *
 * @author tienanh
 * @version 1.0
 */
@EFragment(R.layout.fragmenthome)
public class HomeFragment extends Fragment implements CustomVideoHome.OnPlayYtbViDeo, VideoControllerView.MediaPlayerControl, RelatedVideoAdapter.OnPlayYtbViDeo {

    private List<Items.ItemsEntity> mItems = new ArrayList<>();
    private CustomVideoHome mAdapterVideo;
    @ViewById(R.id.recyclerViewVideo)
    RecyclerView mRecyclerViewVideo;
    private ProgressDialog mProgressDialog;
    public static final String PLAYLIST_KEY = "PLdYSWqTrWP2jyqWIdjsATbrb11uN_BMrF";
    public static final int MAX_ITEMS = 41;
    public static final String ITEM_TYPE = "video";
    public static final String API_SNIPPET = "snippet";
    private static final int DELAY_MILLIS = 10;
    //for DraggableView

    @ViewById(R.id.recyclerVideoRelated)
    RecyclerView mRecycleView;

    @ViewById(R.id.video_detail)
    VideoView mVideoView;

    @ViewById(R.id.layout_video_frame)
    RelativeLayout mVideoFrame;

    @ViewById(R.id.videoLoading)
    View mProgressBar;
    //    @Extra("Video_ID")
    private String ytbVideoId;
    //    @Extra("Video_Thumbs")
    private String ytbVideoThumb;
    private LinearLayoutManager linearLayoutManager;
    private static final float DOUBLE_SPEED = 1.4f;
    private ArrayList<String> mUrls = new ArrayList<>();
    private final String BASE_URL = "http://keepvid.com/?url=https://www.youtube.com/watch?v=";
    private VideoControllerView mController;
    private int mVideoDuration;
    private int mVideoPosition;
    private Drawable mDrawable = null;
    private RelatedVideoAdapter mAdapter;
    private ArrayList<Video.ItemsEntity> mDatas = new ArrayList<>();
    private ProgressDialog mProgress;

    private static final int MAX_RESULT = 30;
    private static final String TYPE = "video";
    private static final String API_KEY = "AIzaSyBtEYk0NLi1DcEjYx8Z1TDDWulmmTajV4s";
    private static final String PART = "snippet";
    @ViewById(R.id.draggable_view)
    DraggableView mDraggableView;

    @AfterViews
    void contentLayout() {

        initializeDraggableView();
        hookDraggableViewListener();


        loadData();
        contentAdapter();
//Inside  layout
        setValue();
        configRecycleView();
        initMedia();
        setAdapter();
    }

    //=====================

    private void setValue() {
        mController = new VideoControllerView(getActivity(), false);
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer arg0) {
            }
        });
    }

    //config recycleView
    private void configRecycleView() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(linearLayoutManager);
    }

    private void initMedia() {
        mVideoView.setOnPreparedListener(mOnPreparedListener);
    }

    private MediaPlayer.OnPreparedListener mOnPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            mVideoDuration = 0;
            mVideoDuration = mVideoView.getDuration();
            mProgressBar.setVisibility(View.GONE);
            mController.setMediaPlayer(HomeFragment.this);
            mController.setAnchorView(mVideoFrame);
            if (mDrawable != null)
                mController.setBackgroundDrawable(mDrawable);
            mController.show();
//            mVideoView.seekTo(mVideoPosition);
        }
    };

    private void setAdapter() {
        mAdapter = new RelatedVideoAdapter(getActivity(), mDatas, this);
        mRecycleView.setAdapter(mAdapter);
    }


    class RequestTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mController.hide();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... uri) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            try {
                response = httpclient.execute(new HttpGet(uri[0]));
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    responseString = out.toString();
                    out.close();
                } else {
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (ClientProtocolException e) {
                //TODO Handle problems..
            } catch (IOException e) {
                //TODO Handle problems..
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Parser HTML
            Document doc = Jsoup.parse(result);
            Element div = doc.select("div[id=dl").first();
            if (div != null) {
                mUrls.clear();
                for (int i = 0; i < div.childNodeSize(); i++) {
                    Element link = div.getElementsByIndexEquals(i).select("a[class=l]").first();
                    if (link != null && link.text().equals("» Download MP4 «")) {
                        String url = link.attr("href");
                        mUrls.add(url);
                    }
                }
                mVideoView.setVideoPath(mUrls.get(0));
            }
            getRelatedVideo();
        }
    }

    @Override
    public void start() {
        if (!mVideoView.isPlaying()) {
            mController.setBackgroundDrawable(null);
            mVideoView.start();
        }
    }

    @Override
    public void pause() {
        if (mVideoView.isPlaying())
            mVideoView.pause();
        else
            mVideoView.resume();
    }

    @Override
    public int getDuration() {
        return mVideoDuration;
    }

    @Override
    public int getCurrentPosition() {
        return mVideoView.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {
        mVideoView.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return mVideoView.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return mVideoView.getBufferPercentage();
    }

    @Override
    public boolean canPause() {
        return mVideoView.canPause();
    }

    @Override
    public void fullScreenToggle() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    @Override
    public void playNormalVideo() {
        mVideoPosition = (int) (mVideoView.getCurrentPosition() * DOUBLE_SPEED);
    }

    @Override
    public void playDoubleSpeedVideo() {

    }

    @Override
    public void onPause() {
        if (mVideoView.isPlaying()) {
            mController.updatePausePlay();
        }
        mVideoView.stopPlayback();
        super.onPause();
    }

    private class LoadBackground extends AsyncTask<String, Void, Drawable> {

        private String imageUrl, imageName;

        public LoadBackground(String url, String file_name) {
            this.imageUrl = url;
            this.imageName = file_name;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Drawable doInBackground(String... urls) {

            try {
                InputStream is = (InputStream) this.fetch(this.imageUrl);
                Drawable d = Drawable.createFromStream(is, this.imageName);
                return d;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        private Object fetch(String address) throws MalformedURLException, IOException {
            URL url = new URL(address);
            Object content = url.getContent();
            return content;
        }

        @SuppressWarnings("deprecation")
        @Override
        protected void onPostExecute(Drawable result) {
            super.onPostExecute(result);
            mDrawable = result;
        }
    }

    private void getRelatedVideo() {
        if (mDatas != null)
            mDatas.clear();
        mProgress = new ProgressDialog(getActivity());
        mProgress.setMessage("Loading...");
        mProgress.show();
        AuthApi.getRelatedVideo(PART, MAX_RESULT, ytbVideoId, TYPE, API_KEY, new Callback<Video>() {
            @Override
            public void success(Video items) {
                for (int i = 0; i < items.getItems().size(); i++) {
                    mDatas.add(items.getItems().get(i));
                }
                mAdapter.notifyDataSetChanged();
                mProgress.dismiss();
            }

            @Override
            public void failure(RetrofitError error, Error myError) {
                mProgress.dismiss();
            }
        });
    }

    @Override
    public void OnPlayVideo(String videoId, int position, String urlImageThumb) {
        mDraggableView.setVisibility(View.VISIBLE);
        mDraggableView.maximize();


        if (mVideoView != null) {
            mVideoView.pause();
            mVideoView.stopPlayback();
            mVideoView.setBackgroundDrawable(null);
            new LoadBackground(urlImageThumb, "ThumbImage").execute();
            getHTMLCode(BASE_URL + videoId);
        }
    }

    public void getHTMLCode(String url) {
        //Request to get HTML code
        new RequestTask().execute(url);
    }

    //=====================
    private void contentAdapter() {
        StaggeredGridLayoutManager mGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        mRecyclerViewVideo.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewVideo.setHasFixedSize(true);
        mRecyclerViewVideo.setLayoutManager(mGridLayoutManager);
        mAdapterVideo = new CustomVideoHome(getActivity(), mItems, this);
        mRecyclerViewVideo.setAdapter(mAdapterVideo);
    }

    private void loadData() {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        if (!mProgressDialog.isShowing())
            mProgressDialog.show();
        AuthApi.getAllVideo(PLAYLIST_KEY, API_SNIPPET, MAX_ITEMS, ITEM_TYPE, API_KEY, new Callback<Items>() {
            @Override
            public void success(Items item) {
                mItems.addAll(item.getItems());
                mAdapterVideo.notifyDataSetChanged();
                if (getActivity() != null && mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
            }

            @Override
            public void failure(RetrofitError error, Error myError) {
                if (getActivity() != null && mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
            }
        });
    }

    @Override
    public void OnPlayYtbViDeoOutSize(String videoId, int position, String urlImageThumb) {
        ytbVideoId = videoId;
        ytbVideoThumb = urlImageThumb;
        getHTMLCode(BASE_URL + videoId);
        new LoadBackground(urlImageThumb, "ThumbImage").execute();
    }

    private void initializeDraggableView() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDraggableView.setVisibility(View.GONE);
                mDraggableView.closeToRight();
            }
        }, DELAY_MILLIS);
    }


    private void hookDraggableViewListener() {
        mDraggableView.setDraggableListener(new DraggableListener() {
            @Override
            public void onMaximized() {
//                startVideo();
            }

            //Empty
            @Override
            public void onMinimized() {
                //Empty
            }

            @Override
            public void onClosedToLeft() {
//                pauseVideo();
            }

            @Override
            public void onClosedToRight() {
//                pauseVideo();
            }
        });
    }

    /**
     * Pause the VideoView content.
     */
    private void pauseVideo() {
        if (mVideoView.isPlaying()) {
            mVideoView.pause();
        }
    }

    /**
     * Resume the VideoView content.
     */
    private void startVideo() {
        if (!mVideoView.isPlaying()) {
            mVideoView.start();
        }
    }
}