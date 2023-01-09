package com.healer.core.store.storemap.mock;

public class User {

    private String userName;

    private String password;

    private String userNum;

    private String phoneNum;

    private Integer isAdmin;

    private Long age;

    public User() {
    }

    public User(String userName) {
        this.userName = userName;
    }

    public User(String userName, String password, String userNum, String phoneNum, Integer isAdmin, Long age) {
        this.userName = userName;
        this.password = password;
        this.userNum = userNum;
        this.phoneNum = phoneNum;
        this.isAdmin = isAdmin;
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }
}
