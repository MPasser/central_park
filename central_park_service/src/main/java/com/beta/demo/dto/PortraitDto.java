package com.beta.demo.dto;

import java.io.InputStream;

public class PortraitDto {
    private InputStream inputStream; // 头像的输入流
    private String filename; // 头像的名称
    private String uploadPath; // 头像的上传路径

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }
}
