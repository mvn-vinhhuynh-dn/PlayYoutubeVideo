package com.example.asiantech.playyoutubevideo.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Copyright © 2015 AsianTech inc.
 * Created by asiantech on 8/10/15.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Login extends BaseModel {
    private Data data = new Data();
    @SerializedName("return")
    @Expose
    private Boolean _return;

    /**
     * sub class object data
     */
    @lombok.Data
    public class Data {
        private User user = new User();

        /**
         * sub class object user
         */
        @lombok.Data
        public class User {
            private int id;
            private String email;
            private String access_token;
            private String open_tok_api_key;
        }
    }
}
