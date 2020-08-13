package com.beta.demo.service;

import com.beta.demo.dto.ChatMessageDto;
import com.beta.demo.exception.ChatMessageIsEmptyException;
import com.beta.demo.pojo.ChatMessage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ChatMessageService {

    /**
     * 添加一条文本消息到数据库
     * @param chatMessage
     */
    void addText(ChatMessage chatMessage) throws ChatMessageIsEmptyException;

    /**
     * 添加一条文件消息到数据库
     * @param chatMessageDto
     * @return
     */
    String addFile(ChatMessageDto chatMessageDto) throws IOException;

    /**
     * 查找消息记录
     * @param scope
     * @return
     */
    List<ChatMessage> findMsgLog(String scope);



}
