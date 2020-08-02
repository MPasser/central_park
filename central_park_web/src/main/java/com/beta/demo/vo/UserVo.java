package com.beta.demo.vo;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UserVo {
    private String username;
    private String password;
    private CommonsMultipartFile portrait;

    private String email;




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

    public CommonsMultipartFile getPortrait() {
        return portrait;
    }

    public void setPortrait(CommonsMultipartFile portrait) {
        this.portrait = portrait;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
