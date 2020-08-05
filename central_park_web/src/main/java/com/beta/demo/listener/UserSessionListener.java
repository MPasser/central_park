package com.beta.demo.listener;

import com.beta.demo.pojo.User;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.*;

// FIXME : 等待移除，计划使用websocket存储http session对象

@WebListener
public class UserSessionListener implements HttpSessionListener {

    private static final Map<String, HttpSession> sessions = new HashMap<>();


    public List<HttpSession> getActiveSessions() {
        return new ArrayList<>(sessions.values());
    }

    public Set<User> getActiveUsers() {
        Set<User> activeUsers = new HashSet<>();
        for (HttpSession hs : getActiveSessions()) {
            User u = (User) hs.getAttribute("user");
            if (null != u) { // session具有user属性，且user的id与自己的id不同
                activeUsers.add(u);
            }
        }
        return activeUsers;
    }

    // private UserService userService;
    //
    // @Autowired
    // public UserSessionListener(UserService userService) {
    //     this.userService = userService;
    // }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setMaxInactiveInterval(30);
        sessions.put(session.getId(), session);
        System.out.println("sessionCreated 执行了,sessions长度：" + sessions.size());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("sessionDestroyed 执行了,sessions长度：" + sessions.size());
        HttpSession session = se.getSession();
        sessions.remove(session.getId());
    }

}
