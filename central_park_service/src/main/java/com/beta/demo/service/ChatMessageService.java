package com.beta.demo.service;

import com.beta.demo.dto.ChatMessageDto;
import com.beta.demo.pojo.ChatMessage;

import java.io.FileNotFoundException;

public interface ChatMessageService {

    /**
     * 添加一条文本消息到数据库
     * @param chatMessage
     */
    void addText(ChatMessage chatMessage);

    /**
     * 添加一条文件消息到数据库
     * @param chatMessageDto
     * @return
     */
    String addFile(ChatMessageDto chatMessageDto) throws FileNotFoundException;

}
