package com.example.ldpserver.util;

import com.example.ldpserver.cache.UserCacheManager;
import com.example.ldpserver.cache.UserInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class FileUtil {

    // ClassPathResource方式
    public static final ClassPathResource USER_STREAM = new ClassPathResource("login_plugin/userInfo.txt");
    private static final Gson gson = new Gson();

    private static final String path = "C:/Users/Administrator/Desktop/server/userInfo.txt";


    public synchronized static void readUserFile() {
        try {
            File file = new File(path);
            //   BufferedReader reader = new BufferedReader(new InputStreamReader(USER_STREAM.getInputStream(), StandardCharsets.UTF_8));
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            ArrayList<UserInfo> userInfos;
            String result = builder.toString();
            if (result.length() == 0) {
                userInfos = new ArrayList<>();
            } else {
                userInfos = gson.fromJson(result, new TypeToken<ArrayList<UserInfo>>() {}.getType());
            }
            UserCacheManager.getInstance().setUserInfoCacheList(userInfos);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public synchronized static void writeUserFile(ArrayList<UserInfo> userInfos){
        // FileWriter的第二个参数代表是否追加
        BufferedWriter writer = null;
        try {
            String json = gson.toJson(userInfos, new TypeToken<ArrayList<UserInfo>>() {}.getType());
            File file = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            writer.write(json);
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
