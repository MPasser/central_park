package com.beta.demo.service;

import com.beta.demo.dto.UserDto;
import com.beta.demo.exception.UserAlreadyExists;
import com.beta.demo.pojo.User;
import org.apache.commons.fileupload.FileUploadException;

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
     * select user by username
     *
     * @param username
     * @return
     */
    User findByUsername(String username);


    /**
     * insert user
     *
     * @param userDto
     */
    void add(UserDto userDto) throws FileUploadException, UserAlreadyExists;


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
