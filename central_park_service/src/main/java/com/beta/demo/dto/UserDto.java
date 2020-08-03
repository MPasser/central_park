package com.beta.demo.dto;

import java.io.InputStream;
import java.util.Date;

public class UserDto {
    private String id;
    private String username;
    private String password;

    private InputStream portraitInputStream; // 头像的输入流
    private String portraitFilename; // 头像的名称
    private String portraitUploadPath; // 头像的上传路径


    private Integer onlineState;
    private Date registerDate;

    private boolean gender; // true means male , false means female
    private String email;


    @Override
    public String toString() {
        return "UserDto{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", portraitInputStream=" + portraitInputStream +
                ", portraitFilename='" + portraitFilename + '\'' +
                ", portraitUploadPath='" + portraitUploadPath + '\'' +
                ", onlineState=" + onlineState +
                ", registerDate=" + registerDate +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public InputStream getPortraitInputStream() {
        return portraitInputStream;
    }

    public void setPortraitInputStream(InputStream portraitInputStream) {
        this.portraitInputStream = portraitInputStream;
    }

    public String getPortraitFilename() {
        return portraitFilename;
    }

    public void setPortraitFilename(String portraitFilename) {
        this.portraitFilename = portraitFilename;
    }

    public String getPortraitUploadPath() {
        return portraitUploadPath;
    }

    public void setPortraitUploadPath(String portraitUploadPath) {
        this.portraitUploadPath = portraitUploadPath;
    }

    public Integer getOnlineState() {
        return onlineState;
    }

    public void setOnlineState(Integer onlineState) {
        this.onlineState = onlineState;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
