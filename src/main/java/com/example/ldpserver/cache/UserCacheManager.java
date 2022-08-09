package com.example.ldpserver.cache;

import com.example.ldpserver.util.FileUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class UserCacheManager {

    // 模拟缓存
    private static final ArrayList<UserInfo> userInfoCacheList = new ArrayList<>(10);
    // 模拟 redis
    private static final HashMap<String, UserInfo> userInfoCacheMap = new HashMap<>(10);

    private static final class Manager {
        private static final UserCacheManager Instance = new UserCacheManager();
    }

    private UserCacheManager() {
    }

    public static UserCacheManager getInstance() {
        return Manager.Instance;
    }

    public void setUserInfoCacheList(ArrayList<UserInfo> data) {
        userInfoCacheList.clear();
        userInfoCacheList.addAll(data);
        userInfoCacheMap.clear();
        for (UserInfo userInfo : data) {
            userInfoCacheMap.put(userInfo.getUserId(), userInfo);
        }
    }

    public void addNewUser(UserInfo userInfo) {
        if (userInfo == null) return;
        userInfoCacheList.add(userInfo);
        userInfoCacheMap.put(userInfo.getUserId(), userInfo);
        FileUtil.writeUserFile(userInfoCacheList);
    }

    public boolean checkUserExist(String userId) {
        return userInfoCacheMap.containsKey(userId);
    }

    public boolean checkUserAndPwd(String userId, String pwd) {
        UserInfo userInfo = userInfoCacheMap.get(userId);
        if (userInfo != null) {
            return equals(userId, userInfo.getUserId()) && equals(pwd, userInfo.getUserPwd());
        }
        return false;
    }


    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

}
