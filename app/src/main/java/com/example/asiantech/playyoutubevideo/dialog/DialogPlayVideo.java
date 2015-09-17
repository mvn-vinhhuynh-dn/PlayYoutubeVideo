package com.example.asiantech.playyoutubevideo.dialog;


import android.app.DialogFragment;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.example.asiantech.playyoutubevideo.R;
import com.example.asiantech.playyoutubevideo.mediaPlayerCustomView.VideoControllerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
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


/**
 * Copyright © 2015 AsianTech inc.
 * Created by VinhHlb on 9/11/15.
 */

@EFragment(R.layout.dialog_playvideo)
public class DialogPlayVideo extends DialogFragment implements VideoControllerView.MediaPlayerControl {

    @FragmentArg
    String ytbVideoId;
    @FragmentArg
    String ytbVideoThumb;

    @ViewById(R.id.video_detail)
    VideoView mVideoView;

    @ViewById(R.id.layout_video_frame)
    RelativeLayout mVideoFrame;

    @ViewById(R.id.videoLoading)
    View mProgressBar;

    private static final float DOUBLE_SPEED = 1.4f;
    private ArrayList<String> mUrls = new ArrayList<>();
    private final String BASE_URL = "http://keepvid.com/?url=https://www.youtube.com/watch?v=";
    private VideoControllerView mController;
    private int mVideoDuration;
    private int mVideoPosition;
    private Drawable mDrawable = null;

    @AfterViews
    void afterView() {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        setValue();
        initMedia();
        getHTMLCode(BASE_URL + ytbVideoId);
        new LoadBackground(ytbVideoThumb, "ThumbImage").execute();
    }

    public void getHTMLCode(String url) {
        //Request to get HTML code
        new RequestTask().execute(url);
    }

    private void setValue() {
        mController = new VideoControllerView(getActivity(), false);
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer arg0) {
            }
        });
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
            mController.setMediaPlayer(DialogPlayVideo.this);
            mController.setAnchorView(mVideoFrame);
            if (mDrawable != null)
                mController.setBackgroundDrawable(mDrawable);
            mController.show();
//            mVideoView.seekTo(mVideoPosition);
        }
    };

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
}