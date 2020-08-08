package com.beta.demo.controller;

import com.beta.demo.constant.ChatMessageConstant;
import com.beta.demo.dto.ChatMessageDto;
import com.beta.demo.pojo.ChatMessage;
import com.beta.demo.pojo.User;
import com.beta.demo.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Controller
public class ChatMessageController {

    private ChatMessageService chatMessageService;

    @Autowired
    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @RequestMapping(value = "/sendTextMessage", method = RequestMethod.POST)
    @ResponseBody
    public String sendTextMessage(String textMessage, HttpSession httpSession) {

        System.out.println("textMessage:" + textMessage);

        User user = (User) httpSession.getAttribute("selfUser");

        // check if there is user info in session
        if (null == user) {
            // TODO : 这里可能需要做一些跳转处理
            System.out.println("user is null!");
            return "ERROR:用户名为空";
        }

        ChatMessage chatMessage = new ChatMessage();

        // get from default
        chatMessage.setId(UUID.randomUUID().toString());
        chatMessage.setSentTime(new Date());
        chatMessage.setMessageType(ChatMessageConstant.CHAT_MESSAGE_TYPE_TEXT);

        // get from parameters
        chatMessage.setUser(user);
        chatMessage.setMessagePayload(textMessage);


        // add message
        chatMessageService.addText(chatMessage);

        return "OK:发送成功";

    }

    @RequestMapping(value = "/sendFileMessage", method = RequestMethod.POST)
    @ResponseBody
    public String sendFileMessage(@RequestParam CommonsMultipartFile fileMessage, HttpServletRequest req, HttpSession httpSession) {

        String uploadPath = httpSession.getServletContext().getRealPath(ChatMessageConstant.MSG_FILE_UPLOAD_PATH);

        System.out.println("fileMessage:" + fileMessage);

        User user = (User) httpSession.getAttribute("selfUser");

        // check if there is user info in session
        if (null == user) {
            // TODO : 这里可能需要做一些跳转处理
            System.out.println("用户名为空!");
            return "ERROR:用户名为空";
        }
        if (null == fileMessage.getOriginalFilename() || "".equals(fileMessage.getOriginalFilename())){
            // TODO : 这里可能需要做一些跳转处理
            System.out.println("文件名为空!");
            return "ERROR:文件名为空";
        }

        ChatMessageDto chatMessageDto = new ChatMessageDto();

        // get from default
        chatMessageDto.setId(UUID.randomUUID().toString());
        chatMessageDto.setSentTime(new Date());
        chatMessageDto.setMessageType(ChatMessageConstant.CHAT_MESSAGE_TYPE_FILE);

        // get from parameters
        chatMessageDto.setUser(user);

        // process the file
        try {
            chatMessageDto.setFileMessageInputStream(fileMessage.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        chatMessageDto.setFileMessageName(fileMessage.getOriginalFilename());
        chatMessageDto.setFileMessageUploadPath(uploadPath);
        String filenameAndPath = null;

        try {
            filenameAndPath = chatMessageService.addFile(chatMessageDto);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return "ECHO:MESSAGE:FILE:" + filenameAndPath;

    }

}
