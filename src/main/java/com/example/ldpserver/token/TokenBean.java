package com.example.ldpserver.token;

public class TokenBean {

    private String userId;
    private String userName;
    private long refreshTime;

    public TokenBean(String userId, String userName, long refreshTime) {
        this.userId = userId;
        this.userName = userName;
        this.refreshTime = refreshTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(long refreshTime) {
        this.refreshTime = refreshTime;
    }
}
