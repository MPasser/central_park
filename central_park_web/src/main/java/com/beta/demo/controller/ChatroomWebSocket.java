package com.beta.demo.controller;

import com.alibaba.fastjson.JSON;
import com.beta.demo.config.GetHttpSessionConfigurator;
import com.beta.demo.constant.ChatMessageConstant;
import com.beta.demo.vo.UserLessVo;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/chatroomWebSocket", configurator = GetHttpSessionConfigurator.class)
public class ChatroomWebSocket {
    private static CopyOnWriteArraySet<ChatroomWebSocket> webSockets = new CopyOnWriteArraySet<>();
    private static CopyOnWriteArraySet<UserLessVo> activeUsers = new CopyOnWriteArraySet<>();

    private Session session;

    private HttpSession httpSession;
    private UserLessVo userLessVo;


    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {

        HttpSession openHttpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        if (null != openHttpSession) {
            checkIfAlreadyLogin(openHttpSession);

        }

        this.session = session;
        this.httpSession = openHttpSession;
        this.userLessVo = (UserLessVo) this.httpSession.getAttribute("selfUser");
        webSockets.add(this);
        activeUsers.add(this.userLessVo);


        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());


        for (ChatroomWebSocket item : webSockets) {
            String jsonActiveUsers = JSON.toJSONString(activeUsers);
            try {
                item.session.getBasicRemote().sendText("USER:" + jsonActiveUsers);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    /**
     * 检查
     *
     * @param hs
     */
    private void checkIfAlreadyLogin(HttpSession hs) {
        UserLessVo u = (UserLessVo) hs.getAttribute("selfUser");

        if (null != u) {
            System.out.println("正在检查user:" + u.getUsername() + " 是否登录");
            System.out.println("userID:" + u.getId());


            for (ChatroomWebSocket ws : webSockets) { // 取出所有ws session的http session获取其User id进行比对

                UserLessVo PrevUser = (UserLessVo) ws.httpSession.getAttribute("selfUser");

                System.out.println("正在与用户ID " + PrevUser.getId() + "进行比对");
                if (u.getId().equals(PrevUser.getId())) { // 若比对成功
                    if (hs.getId().equals(ws.httpSession.getId())) { // 先判断是否为同一个浏览器，若是，则仅关闭websocket

                    } else { // 若不是，则销毁前一个浏览器的http session
                        ws.httpSession.invalidate();
                        System.out.println("销毁了该http session");
                    }

                    try {
                        ws.session.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("销毁了该websocket session");
                }
            }

        }
    }


    @OnClose
    public void onClose() {
        webSockets.remove(this);
        activeUsers.remove(this.userLessVo);


        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());

        for (ChatroomWebSocket item : webSockets) {
            String jsonActiveUsers = JSON.toJSONString(activeUsers);
            try {
                // FIXME : 这里把用户的密码直接传过去了，虽然是密文，但也不太好，考虑创建新的VO
                item.session.getBasicRemote().sendText("USER:" + jsonActiveUsers);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息：" + message);
        if (message.length() > ChatMessageConstant.CHAT_MESSAGE_MAX_LENGTH) {
            try {
                this.sendMessage("WARNING:消息过长，未发送成功");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //"ECHO:MESSAGE:FILE:" + chatMessageDto.getFileMessageName() + ":" + filepath;
        if (message.startsWith("ECHO")) {
            message = message.substring(5) + ":" + this.userLessVo.getUsername(); // 去除前缀"ECHO:"
        } else {
            message = "MESSAGE:TEXT:" + this.userLessVo.getUsername() + ":" + message;
        }


        for (ChatroomWebSocket item : webSockets) {


            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发送错误");
        error.printStackTrace();
    }

    public int getOnlineCount() {
        return webSockets.size();
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


}
