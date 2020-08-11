package com.beta.demo.service;

import com.beta.demo.dto.PortraitDto;
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

    /**
     * 修改用户基本信息，包括：
     * 用户名
     * 性别
     * 邮箱
     * @param user
     * @throws UserModificationException
     */
    void modifyBasicInfo(User user) throws UserModificationException;


    /**
     * 根据用户id寻找并返回密码
     * @param id
     * @return
     */
    String findPasswordById(String id);

    /**
     * 为指定id的User修改密码
     * @param id
     * @param password
     * @throws UserModificationException
     */
    void modifyPassword(String id, String password) throws UserModificationException;

    /**
     * 为指定id的User修改头像
     * @param portraitDto
     */
    void modifyPortrait(String id, PortraitDto portraitDto) throws UserModificationException, FileUploadException;
}
