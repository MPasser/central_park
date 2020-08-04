package com.beta.demo.dao;

import com.beta.demo.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {

    /**
     * select all users
     *
     * @return
     */
    List<User> selectAll();


    /**
     * select unique user by id
     *
     * @param id
     * @return
     */
    User selectById(String id);


    /**
     * select user by username
     *
     * @param username
     * @return
     */
    User selectByUsername(String username);


    /**
     * insert user
     *
     * @param user
     */
    void insert(User user);


    /**
     * delete user by id
     *
     * @param id
     */
    void deleteById(int id);


    /**
     * update user
     *
     * @param user
     */
    void update(User user);


    void updateOnlineStatus(@Param("id") String id, @Param("onlineStatus") int onlineStatus);
}
