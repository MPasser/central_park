package com.beta.demo.dao;

import com.beta.demo.pojo.User;

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
    User selectById(int id);


    /**
     * select users by username
     *
     * @param username
     * @return
     */
    List<User> selectByUsername(String username);


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

}
