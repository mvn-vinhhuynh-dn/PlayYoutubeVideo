package com.example.asiantech.playyoutubevideo.core.api;

import com.example.asiantech.playyoutubevideo.core.SessionStore;
import com.example.asiantech.playyoutubevideo.object.Login;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright © 2015 AsianTech inc.
 * Created by tientun on 7/2/15.
 */
public class MySession extends SessionStore<Login> {
    private static final String KEY = "test-key";
    static final String HEADER_AUTH = "Access-Token";

    @Override
    protected Map<String, String> saveSession(Login data) {
        Map<String, String> session = new HashMap<>();
        session.put("access_token", data.getData().getUser().getAccess_token());
        session.put("open_tok_api_key", String.valueOf(data.getData().getUser().getOpen_tok_api_key()));
        session.put("email", data.getData().getUser().getEmail());
        session.put("id", String.valueOf(data.getData().getUser().getId()));
        return session;
    }

    @Override
    protected Login restoreSession(Map<String, ?> savedSession) {
        Login login = new Login();
//        try {
//            login.getData().getUser().setAccess_token((String) savedSession.get("access_token"));
//            login.getData().getUser().setOpen_tok_api_key((String) savedSession.get("open_tok_api_key"));
//            login.getData().getUser().setEmail((String) savedSession.get("email"));
//            login.getData().getUser().setId(Integer.parseInt((String) savedSession.get("id")));
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        }
        return login;
    }

    @Override
    protected Map<String, String> getHeader(Login data) {
        Map<String, String> header = new HashMap<>();
        try {
            header.put(HEADER_AUTH, data.getData().getUser().getAccess_token());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return header;
    }

    @Override
    protected String getPrivateKey() {
        return KEY;
    }

    @Override
    public boolean checkAuthenticated(Login data) {
        try {
            return data.getData().getUser().getAccess_token() != null;
        } catch (NullPointerException e) {
            return false;
        }
    }
}
