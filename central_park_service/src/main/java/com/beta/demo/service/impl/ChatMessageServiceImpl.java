package com.beta.demo.service.impl;

import com.beta.demo.constant.ChatMessageConstant;
import com.beta.demo.dao.ChatMessageDao;
import com.beta.demo.dto.ChatMessageDto;
import com.beta.demo.pojo.ChatMessage;
import com.beta.demo.service.ChatMessageService;
import com.beta.demo.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ChatMessageServiceImpl implements ChatMessageService {

    private ChatMessageDao chatMessageDao;

    @Autowired
    public ChatMessageServiceImpl(ChatMessageDao chatMessageDao) {
        this.chatMessageDao = chatMessageDao;
    }

    /**
     * 添加一条文本消息到数据库
     *
     * @param chatMessage
     */
    @Override
    public void addText(ChatMessage chatMessage) {
        chatMessageDao.insert(chatMessage);
    }

    /**
     * 添加一条文件消息到数据库
     *
     * @param chatMessageDto
     * @return
     */
    @Override
    public String addFile(ChatMessageDto chatMessageDto) throws FileNotFoundException {
        System.out.println(chatMessageDto);

        //上传文件
        String filename = StringUtils.renameFilename(chatMessageDto.getFileMessageName(), chatMessageDto.getFileMessageUploadPath());
        String filepath = chatMessageDto.getFileMessageUploadPath() + File.separator + filename;
        try {
            // FIXME : 理应抛出
            StreamUtils.copy(chatMessageDto.getFileMessageInputStream(), new FileOutputStream(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ChatMessage chatMessage = new ChatMessage();

        // transfer DTO to POJO
        chatMessage.setId(chatMessageDto.getId());
        chatMessage.setUser(chatMessageDto.getUser());
        chatMessage.setSentTime(chatMessageDto.getSentTime());
        chatMessage.setMessageType(chatMessageDto.getMessageType());
        chatMessage.setMessagePayload(filename);
        chatMessage.setMessageRef(ChatMessageConstant.MSG_FILE_DOWNLOAD_PATH + File.separator + filename);

        chatMessageDao.insert(chatMessage);

        return filename + ":" + chatMessage.getMessageRef();

    }

    @Override
    public List<ChatMessage> findMsgLog(String scope) {
        List<ChatMessage> msgLog = new ArrayList<>();

        if (ChatMessageConstant.MSG_LOG_SCOPE_ALL.equals(scope)) {
            msgLog = chatMessageDao.selectAll();
        } else if (ChatMessageConstant.CHAT_MESSAGE_TYPE_FILE.equals(scope)) {
            msgLog = chatMessageDao.selectByMessageType(ChatMessageConstant.CHAT_MESSAGE_TYPE_FILE);
        }

        return msgLog;
    }
}
