package com.example.ldpserver.requestutils;

import com.google.gson.Gson;

public class JsonUtil {

    private static final Gson gson = new Gson();

    public static <T> T toDataBean(String response, Class<T> clazz) {
        try {
            return gson.fromJson(response, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
