package com.example.asiantech.playyoutubevideo.core.api;

import com.example.asiantech.playyoutubevideo.core.ApiClient;
import com.example.asiantech.playyoutubevideo.core.Callback;
import com.example.asiantech.playyoutubevideo.object.Items;
import com.example.asiantech.playyoutubevideo.object.Video;

import java.lang.Error;

import retrofit.RetrofitError;

/**
 * Created by anhtt on 08/09/2015.
 */
public class AuthApi {

    public static void getAllVideo(String playlistId, String part, int numItem, String type, String key, final Callback<Items> callback) {
        ApiClient.call().getAllVideo(playlistId, part, numItem, type, key, new Callback<Items>() {
            @Override
            public void success(Items items) {
                callback.success(items);
            }

            @Override
            public void failure(RetrofitError error, Error myError) {
                callback.failure(error, myError);
            }
        });
    }


    public static void getRelatedVideo(String part, int maxResult, String idVideo, String type, String key, final Callback<Video> callback) {
        ApiClient.call().getRelatedVideo(part, maxResult, idVideo, type, key, new Callback<Video>() {
            @Override
            public void success(Video items) {
                callback.success(items);
            }

            @Override
            public void failure(RetrofitError error, Error myError) {
                callback.failure(error, myError);
            }
        });
    }
}
