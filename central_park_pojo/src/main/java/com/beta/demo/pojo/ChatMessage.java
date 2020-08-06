package com.beta.demo.pojo;

import java.io.Serializable;
import java.util.Date;

public class ChatMessage implements Serializable {
    private String id;
    private User user;
    private Date sentTime;
    private Integer messageType;
    private String messagePayload;
    private String messageRef;

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

    public String getMessagePayload() {
        return messagePayload;
    }

    public void setMessagePayload(String messagePayload) {
        this.messagePayload = messagePayload;
    }

    public String getMessageRef() {
        return messageRef;
    }

    public void setMessageRef(String messageRef) {
        this.messageRef = messageRef;
    }
}
