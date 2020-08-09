package com.beta.demo.controller;

import com.beta.demo.constant.ChatMessageConstant;
import com.beta.demo.constant.PaginationConstant;
import com.beta.demo.dto.ChatMessageDto;
import com.beta.demo.pojo.ChatMessage;
import com.beta.demo.pojo.User;
import com.beta.demo.service.ChatMessageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class ChatMessageController {

    private ChatMessageService chatMessageService;

    @Autowired
    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }


    /**
     * 将文本消息存入数据库中
     *
     * @param textMessage
     * @param httpSession
     * @return
     */
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


    /**
     * 将文件数据存入数据库中
     *
     * @param fileMessage
     * @param req
     * @param httpSession
     * @return ECHO:开头的字符串，指示浏览器向websocket发送ECHO:之后的字符串，以请求在页面上显示发送的文件信息
     */
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
        if (null == fileMessage.getOriginalFilename() || "".equals(fileMessage.getOriginalFilename())) {
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


    @RequestMapping(value = "/messageLog")
    private ModelAndView showMsgLog(String scope, Integer pageNum, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("selfUser");

        // TODO : 把其他的空值判断都换成这个
        if (ObjectUtils.isEmpty(pageNum)) {
            pageNum = PaginationConstant.PAGE_NUM;
        }

        if (ObjectUtils.isEmpty(scope)) {
            scope = ChatMessageConstant.MSG_LOG_SCOPE_ALL;
        }
        ModelAndView mav = new ModelAndView();

        if (null == user) {
            System.out.println("用户名为空!");
            mav.addObject("failTitle", "查询失败");
            mav.addObject("message", "用户尚未登录");
            mav.setViewName("fail-info");
            return mav;
        }



        PageHelper.startPage(pageNum, PaginationConstant.PAGE_SIZE);
        List<ChatMessage> msgLog = chatMessageService.findMsgLog(scope);
        PageInfo<ChatMessage> pageInfo = new PageInfo<>(msgLog);


        System.out.println("PageInfo.getPageSize:" + pageInfo.getPageSize());
        System.out.println("PageInfo.getPageNum:" + pageInfo.getPageNum());
        System.out.println("PageInfo.getPages:" + pageInfo.getPages());

        mav.addObject("pageInfo", pageInfo);
        mav.setViewName("chatMsgLog");

        return mav;

    }

}
