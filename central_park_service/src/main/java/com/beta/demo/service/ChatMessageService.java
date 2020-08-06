package com.beta.demo.service;

import com.beta.demo.pojo.ChatMessage;

public interface ChatMessageService {

    /**
     * 添加一条消息到数据库
     * @param chatMessage
     */
    void add(ChatMessage chatMessage);

}
