package com.beta.demo.controller;

import com.beta.demo.constant.ChatMessageConstant;
import com.beta.demo.pojo.ChatMessage;
import com.beta.demo.pojo.User;
import com.beta.demo.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.UUID;

@Controller
public class ChatMessageController {

    private ChatMessageService chatMessageService;

    @Autowired
    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @RequestMapping("/sendTextMessage")
    @ResponseBody
    public void sendTextMessage(String chatTextMessage, HttpSession httpSession){

        System.out.println("chatTextMessage:" + chatTextMessage);

        User user = (User) httpSession.getAttribute("selfUser");

        // check if there is user info in session
        if (null == user){
            // TODO : 这里可能需要做一些跳转处理，设置返回参数为ModelAndView会不会太浪费？
            System.out.println("user is null!");
            return;
        }

        ChatMessage chatMessage = new ChatMessage();

        // get from default
        chatMessage.setId(UUID.randomUUID().toString());
        chatMessage.setSentTime(new Date());
        chatMessage.setMessageType(ChatMessageConstant.CHAT_MESSAGE_TYPE_TEXT);

        // get from parameters
        chatMessage.setUser(user);
        chatMessage.setMessagePayload(chatTextMessage);


        // add message
        chatMessageService.add(chatMessage);

    }

}
