package com.beta.demo.service;

import com.beta.demo.pojo.User;

import java.util.List;

public interface UserService {
    /**
     * select all users
     *
     * @return
     */
    List<User> findAll();


    /**
     * select unique user by id
     *
     * @param id
     * @return
     */
    User findById(int id);


    /**
     * select users by username
     *
     * @param username
     * @return
     */
    List<User> findByUsername(String username);


    /**
     * insert user
     *
     * @param user
     */
    void add(User user);


    /**
     * delete user by id
     *
     * @param id
     */
    void removeById(int id);


    /**
     * update user
     *
     * @param user
     */
    void modify(User user);
}
