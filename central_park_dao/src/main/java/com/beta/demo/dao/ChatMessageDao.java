package com.beta.demo.dao;


import com.beta.demo.pojo.ChatMessage;

import java.util.List;

public interface ChatMessageDao {

    /**
     * select all items from database
     * @return
     */
    List<ChatMessage> selectAll();


    /**
     * select items of specific type
     * @param messageType
     * @return
     */
    List<ChatMessage> selectByMessageType(Integer messageType);


    /**
     * insert a item into database
     * @param chatMessage
     */
    void insert(ChatMessage chatMessage);

}
