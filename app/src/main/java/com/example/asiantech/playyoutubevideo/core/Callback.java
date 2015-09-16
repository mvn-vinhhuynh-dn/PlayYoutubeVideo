package com.example.asiantech.playyoutubevideo.core;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Copyright © 2015 AsianTech inc.
 * Created by asianTech on 7/27/15.
 */
public abstract class Callback<T> implements retrofit.Callback<T> {

    private static final String TAG = Callback.class.getSimpleName();

    public Callback() {
    }

    public abstract void success(T t);

    public abstract void failure(RetrofitError error, Error myError);

    @Override
    public void success(T t, Response response) {
        if (t.getClass().equals(ApiClient.getInstance().getSessionStore().getSessionClass())) {
            ApiClient.getInstance().saveSession(t);
        }
        success(t);
    }

    public void failure(RetrofitError error) {
        if (error.getResponse() != null) {
//            BaseModel baseModel = (BaseModel) error.getBodyAs(BaseModel.class);
//            failure(error, new Error(baseModel.getMeta().getCode(), baseModel.getMeta().getMessages()));
        }
       // failure(error, new Error(0, ""));
    }

}