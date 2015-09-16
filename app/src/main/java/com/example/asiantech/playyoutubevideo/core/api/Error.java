package com.example.asiantech.playyoutubevideo.core.api;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Copyright © 2015 AsianTech inc.
 * Created by tientun on 7/2/15.
 */
@Data
@AllArgsConstructor(suppressConstructorProperties = true)
public class Error {
    private int stateCode;
    private String message;
}
