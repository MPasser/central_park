package com.beta.demo.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// @Component
@WebListener
public class UserSessionListener implements HttpSessionListener {

    private static final Map<String, HttpSession> sessions = new HashMap<>();



    public List<HttpSession> getActiveSessions() {
        return new ArrayList<>(sessions.values());
    }


    // private UserService userService;
    //
    // @Autowired
    // public UserSessionListener(UserService userService) {
    //     this.userService = userService;
    // }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("sessionCreated 执行了");
        HttpSession session = se.getSession();
        session.setMaxInactiveInterval(30);
        sessions.put(session.getId(), session);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("sessionDestroyed 执行了");
        HttpSession session = se.getSession();
        sessions.remove(session.getId());
    }

    // public void sessionLogin(HttpSession loginSession) {
    //     User user = (User) loginSession.getAttribute("user");
    //     if (null != user) {
    //         String userId = user.getId();
    //         // TODO : remove debug
    //         System.out.println("session user id :" + userId);
    //         userService.modifyOnlineStatus(userId, UserConstant.USER_STATE_ONLINE);
    //         System.out.println("userService.modifyOnlineStatus执行了");
    //     } else {
    //         System.out.println("user 为空！位置：sessionLogin");
    //         return; // TODO : 可以抛出异常
    //     }
    // }
}
