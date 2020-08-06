package com.beta.demo.service.impl;

import com.beta.demo.dao.ChatMessageDao;
import com.beta.demo.pojo.ChatMessage;
import com.beta.demo.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ChatMessageServiceImpl implements ChatMessageService {

    private ChatMessageDao chatMessageDao;

    @Autowired
    public ChatMessageServiceImpl(ChatMessageDao chatMessageDao) {
        this.chatMessageDao = chatMessageDao;
    }

    /**
     * 向数据库中添加一条消息记录
     * @param chatMessage
     */
    @Override
    public void add(ChatMessage chatMessage) {
        // TODO : 添加消息类型判断
        chatMessageDao.insert(chatMessage);
    }
}
