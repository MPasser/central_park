package com.beta.demo.config;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * 继承websocket的配置类，将HttpSession放入ServerEndPoint的map中
 */
public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator {
    /**
     * 为了在websocket连接时获取当前的HttpSession，需要重写握手方法
     * @param sec
     * @param request
     * @param response
     */
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        sec.getUserProperties().put(HttpSession.class.getName(),httpSession);
    }
}
