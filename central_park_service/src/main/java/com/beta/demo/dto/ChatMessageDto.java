package com.beta.demo.dto;

import com.beta.demo.pojo.User;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.InputStream;
import java.util.Date;

public class ChatMessageDto {
    private String id;
    private User user;
    private Date sentTime;
    private Integer messageType;
    private InputStream fileMessageInputStream; // 文件的输入流
    private String fileMessageName; // 文件的名称
    private String fileMessageUploadPath; // 文件的上传路径

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getSentTime() {
        return sentTime;
    }

    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public InputStream getFileMessageInputStream() {
        return fileMessageInputStream;
    }

    public void setFileMessageInputStream(InputStream fileMessageInputStream) {
        this.fileMessageInputStream = fileMessageInputStream;
    }

    public String getFileMessageName() {
        return fileMessageName;
    }

    public void setFileMessageName(String fileMessageName) {
        this.fileMessageName = fileMessageName;
    }

    public String getFileMessageUploadPath() {
        return fileMessageUploadPath;
    }

    public void setFileMessageUploadPath(String fileMessageUploadPath) {
        this.fileMessageUploadPath = fileMessageUploadPath;
    }

    @Override
    public String toString() {
        return "ChatMessageDto{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", sentTime=" + sentTime +
                ", messageType=" + messageType +
                ", fileMessageInputStream=" + fileMessageInputStream +
                ", fileMessageName='" + fileMessageName + '\'' +
                ", fileMessageUploadPath='" + fileMessageUploadPath + '\'' +
                '}';
    }
}
