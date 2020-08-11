package com.beta.demo.service;

import com.beta.demo.dto.UserDto;
import com.beta.demo.exception.UserModificationException;
import com.beta.demo.exception.UserRegisterException;
import com.beta.demo.exception.UserLoginException;
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
    User findById(String id);


    /**
     * select user by username
     *
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * select user by username and password
     * @param username
     * @param password
     * @return
     * @throws UserLoginException
     */
    User login(String username, String password) throws UserLoginException;


    /**
     * add user
     * @param userDto
     * @throws FileUploadException
     * @throws UserRegisterException
     */
    void register(UserDto userDto) throws FileUploadException, UserRegisterException;


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

    void modifyUsername(String userId, String voUsername);

    void modifyEmail(String userId, String voEmail);

    void modifyGender(String userId, boolean b);

    void modifyBasicInfo(User user) throws UserModificationException;

    String findPasswordById(String id);

    void modifyPassword(String id, String password) throws UserModificationException;
}
