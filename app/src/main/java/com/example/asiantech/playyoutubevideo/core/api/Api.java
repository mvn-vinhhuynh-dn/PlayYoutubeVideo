package com.example.asiantech.playyoutubevideo.core.api;


import com.example.asiantech.playyoutubevideo.core.Callback;
import com.example.asiantech.playyoutubevideo.object.Items;

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

}
