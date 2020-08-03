package com.beta.demo.service.impl;

import com.beta.demo.dao.UserDao;
import com.beta.demo.dto.UserDto;
import com.beta.demo.exception.UserAlreadyExists;
import com.beta.demo.pojo.User;
import com.beta.demo.service.UserService;
import com.beta.demo.util.StringUtils;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
    public User findById(int id) {
        // TODO
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public User findByUsername(String username) {

        User user = userDao.selectByUsername(username);

        return user;
    }


    /**
     * 添加新用户
     *
     * @param userDto
     * @throws FileUploadException
     */
    @Override
    public void add(UserDto userDto) throws FileUploadException, UserAlreadyExists {
        // TODO : remove debug
        System.out.println(userDto);

        if (null != userDao.selectByUsername(userDto.getUsername())) {
            throw new UserAlreadyExists("用户名" + userDto + "已存在");
        }


        // upload the portrait file
        String filename = StringUtils.renameFilename(userDto.getPortraitFilename());
        String filepath = userDto.getPortraitUploadPath() + File.separator + filename;

        try {
            StreamUtils.copy(userDto.getPortraitInputStream(), new FileOutputStream(filepath));
        } catch (IOException e) {
            throw new FileUploadException("文件上传异常：" + e.getMessage());
        }

        // insert the user
        User user = new User();

        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setPortrait(filepath);
        user.setOnlineState(userDto.getOnlineState());
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
}
