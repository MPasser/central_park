package com.beta.demo.controller;

import com.alibaba.fastjson.JSON;
import com.beta.demo.listener.UserSessionListener;
import com.beta.demo.pojo.User;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/chatroomWebSocket")
public class ChatroomWebSocket {
    private static CopyOnWriteArraySet<ChatroomWebSocket> webSockets = new CopyOnWriteArraySet<>();

    private Session session;

    private Set<User> activeUsers = new UserSessionListener().getActiveUsers();


    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSockets.add(this);
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
        String jsonActiveUsers = JSON.toJSONString(activeUsers);
        for (ChatroomWebSocket item : webSockets){
            try {
                // FIXME : 这里把用户的密码直接传过去了，虽然是密文，但也不太好，考虑创建新的VO
                item.session.getBasicRemote().sendText("USERINFO=" + jsonActiveUsers);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @OnClose
    public void onClose() {
        webSockets.remove(this);
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }


    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息：" + message);
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
