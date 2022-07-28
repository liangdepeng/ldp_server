package com.example.ldpserver.token;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;

public class TokenUtils {

    private final HashMap<String, String> tokenMap = new HashMap<>(10);

    private static final Gson gson = new Gson();

    public static String createToken(String userId, String userName) {
        TokenBean tokenBean = new TokenBean(userId, userName, System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000);
        String json = gson.toJson(tokenBean, TokenBean.class);
        byte[] bytes = Base64.getEncoder().encode(json.getBytes(StandardCharsets.UTF_8));
        return new String(bytes);
    }

    public static TokenBean parseToken(String tk) {
        try {
            byte[] bytes = Base64.getDecoder().decode(tk.getBytes(StandardCharsets.UTF_8));
            String s = new String(bytes);
            return gson.fromJson(s, TokenBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
