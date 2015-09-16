package com.example.asiantech.playyoutubevideo.object;

import lombok.Data;

/**
 * Copyright © 2015 AsianTech inc.
 * Created by asiantech on 8/10/15.
 */
@Data
public class BaseModel {
    private String error;
    private String message;
    private Meta meta;
    /**
     * subclass meta
     */
    @Data
    public class Meta {
        private int code;
        private String status;
        private String messages;
    }

}
