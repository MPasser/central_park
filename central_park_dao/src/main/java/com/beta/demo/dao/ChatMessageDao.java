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
     * insert a item into database
     * @param chatMessage
     */
    void insert(ChatMessage chatMessage);

}
