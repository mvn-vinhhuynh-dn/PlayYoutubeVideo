package com.example.asiantech.playyoutubevideo.core.api;


import com.example.asiantech.playyoutubevideo.core.Callback;
import com.example.asiantech.playyoutubevideo.object.Items;
import com.example.asiantech.playyoutubevideo.object.Video;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by anhtt on 08/09/2015.
 */
public interface Api {

    @GET("/playlistItems")
    void getAllVideo(@Query("playlistId") String playlistId,
                     @Query("part") String part,
                     @Query("maxResults") int numItem,
                     @Query("type") String type,
                     @Query("key") String key, Callback<Items> callback);

    @GET("/search")
    void getRelatedVideo(
            @Query("part") String part,
            @Query("maxResults") int numItem,
            @Query("relatedToVideoId") String id,
            @Query("type") String type,
            @Query("key") String key, Callback<Video> callback);
}
