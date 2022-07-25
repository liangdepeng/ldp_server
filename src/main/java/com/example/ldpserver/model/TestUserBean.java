package com.example.ldpserver.model;

public class TestUserBean {

    private String userId;
    private String userName;
    private String userSex;
    private int userAge;

    // test
    public TestUserBean() {
        setUserId("12321");
        setUserAge(20);
        setUserSex("男");
        setUserName("白雪");
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

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }
}
