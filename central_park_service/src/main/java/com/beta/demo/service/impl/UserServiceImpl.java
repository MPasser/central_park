package com.beta.demo.service.impl;

import com.beta.demo.constant.UserConstant;
import com.beta.demo.dao.UserDao;
import com.beta.demo.dto.PortraitDto;
import com.beta.demo.dto.UserDto;
import com.beta.demo.exception.UserModificationException;
import com.beta.demo.exception.UserRegisterException;
import com.beta.demo.exception.UserLoginException;
import com.beta.demo.pojo.User;
import com.beta.demo.service.UserService;
import com.beta.demo.util.StringUtils;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> findAll() {
        // TODO
        return null;
    }

    @Override
    public User findById(String id) {
        User user = userDao.selectById(id);
        return user;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public User findByUsername(String username) {

        User user = userDao.selectByUsername(username);

        return user;
    }


    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     * @throws UserLoginException
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public User login(String username, String password) throws UserLoginException {
        User user = userDao.selectByUsername(username);

        if (null == user){
            throw new UserLoginException("用户名不正确");
        }else if (password.equals(user.getPassword())){
            return user;
        }else {
            throw new UserLoginException("密码不正确");
        }
    }



    /**
     * 注册新用户
     *
     * @param userDto
     * @throws FileUploadException
     */
    @Override
    public void register(UserDto userDto) throws FileUploadException, UserRegisterException {
        // TODO : remove debug
        System.out.println(userDto);

        // 检查用户名是否已存在
        if (null != userDao.selectByUsername(userDto.getUsername())) {
            throw new UserRegisterException("用户名" + userDto.getUsername() + "已存在");
        }


        // 上传头像文件
        String filename = StringUtils.getRandomFilename(userDto.getPortraitFilename());
        String filepath = userDto.getPortraitUploadPath() + File.separator + filename;

        try {
            StreamUtils.copy(userDto.getPortraitInputStream(), new FileOutputStream(filepath));
        } catch (IOException e) {
            throw new FileUploadException("文件上传异常：" + e.getMessage());
        }

        // 将用户信息插入数据库
        User user = new User();

        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setPortrait(UserConstant.PORTRAIT_DOWNLOAD_PATH + File.separator + filename);
        user.setRegisterDate(userDto.getRegisterDate());

        user.setEmail(userDto.getEmail());
        user.setGender(userDto.getGender());

        userDao.insert(user);

    }

    @Override
    public void removeById(int id) {
        // TODO
    }

    @Override
    public void modify(User user) {
        // TODO
    }

    @Override
    public void modifyUsername(String userId, String voUsername) {

    }

    @Override
    public void modifyEmail(String userId, String voEmail) {

    }

    @Override
    public void modifyGender(String userId, boolean b) {

    }

    @Override
    public void modifyBasicInfo(User user) throws UserModificationException {
        // 检查用户名是否已存在
        User oldUser = userDao.selectById(user.getId());
        if (ObjectUtils.isEmpty(oldUser)) {
            throw new UserModificationException("需要修改的用户" + user.getId() + "不存在");
        }else if (!user.getUsername().equals(oldUser.getUsername())){ // 用户名有变动，则在数据库中查询新姓名
            if (null != userDao.selectByUsername(user.getUsername())) {
                throw new UserModificationException("用户名" + user.getUsername() + "已存在");
            }
        }

        userDao.updateBasicInfo(user);
    }

    @Override
    public String findPasswordById(String id) {
        String password = userDao.selectPasswordById(id);
        return password;
    }

    @Override
    public void modifyPassword(String id, String password) throws UserModificationException {
        User user = userDao.selectById(id);
        if (ObjectUtils.isEmpty(user)) {
            throw new UserModificationException("需要修改的用户" + user.getId() + "不存在");
        }
        userDao.updatePassword(id,password);
    }

    @Override
    public void modifyPortrait(String id, PortraitDto portraitDto) throws UserModificationException, FileUploadException {
        User user = userDao.selectById(id);
        if (ObjectUtils.isEmpty(user)) {
            throw new UserModificationException("需要修改的用户" + user.getId() + "不存在");
        }

        // 上传头像文件
        String filename = StringUtils.getRandomFilename(portraitDto.getFilename());
        String filepath = portraitDto.getUploadPath() + File.separator + filename;

        try {
            StreamUtils.copy(portraitDto.getInputStream(), new FileOutputStream(filepath));
        } catch (IOException e) {
            throw new FileUploadException("文件上传异常：" + e.getMessage());
        }

        String downloadPath = UserConstant.PORTRAIT_DOWNLOAD_PATH + File.separator + filename;

        userDao.updatePortrait(id, downloadPath);
    }

}
