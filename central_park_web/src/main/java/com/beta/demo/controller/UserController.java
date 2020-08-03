package com.beta.demo.controller;

import com.beta.demo.constant.UserConstant;
import com.beta.demo.dto.UserDto;
import com.beta.demo.service.UserService;
import com.beta.demo.vo.UserVo;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/registNewUser")
    public ModelAndView userRegist(@ModelAttribute UserVo userVo, HttpSession session) {

        // TODO : 注册失败时可设置返回一个单独的页面

        ModelAndView mav = new ModelAndView();
        String uploadPath = session.getServletContext().getRealPath(UserConstant.PORTRAIT_UPLOAD_PATH);


        // validate the form elements
        Pattern pattern = Pattern.compile("[\\w]{2,15}");
        Matcher matcher = pattern.matcher(userVo.getUsername());
        if (!matcher.matches()) {
            // mav.addObject("message", "用户名不符合规则");
            mav.setViewName("register");
            return mav;
        }

        pattern = Pattern.compile("[\\w]{6,20}");
        matcher = pattern.matcher(userVo.getPassword());
        if (!matcher.matches()) {
            // mav.addObject("message", "密码不符合规则");
            mav.setViewName("register");
            return mav;
        }

        if ("".equals(userVo.getEmail()) || null == userVo.getEmail()) {
            userVo.setEmail(null);
        } else {
            pattern = Pattern.compile("[\\da-z]+([\\-\\.\\_]?[\\da-z]+)*@[\\da-z]+([\\-\\.]?[\\da-z]+)*(\\.[a-z]{2,})+");
            matcher = pattern.matcher(userVo.getEmail());
            if (!matcher.matches()) {
                // mav.addObject("message", "邮箱不符合规则");
                mav.setViewName("register");
                return mav;
            }
        }

        if ("".equals(userVo.getGender()) || null == userVo.getEmail()) {
            userVo.setEmail(null);
        }


        // transfer VO to DTO
        UserDto userDto = new UserDto();
        // get from default
        userDto.setId(UUID.randomUUID().toString());
        userDto.setOnlineState(UserConstant.USER_STATE_OFFLINE);
        userDto.setRegisterDate(new Date());
        // get from VO
        userDto.setUsername(userVo.getUsername());
        userDto.setPassword(userVo.getPassword());
        userDto.setGender(userVo.getGender());
        userDto.setEmail(userVo.getEmail());
        // process portrait file

        if (userVo.getPortrait().isEmpty()) {
            System.out.println("use default portrait:");
            File defaultPortrait = new File(uploadPath + File.separator + "default-portrait.jpg");
            System.out.println("is able to access default portrait:" + defaultPortrait.exists());
            System.out.println("is able to get the filename:" + defaultPortrait.getName());
            try {
                userDto.setPortraitInputStream(new FileInputStream(defaultPortrait));
                userDto.setPortraitFilename(defaultPortrait.getName());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("use upload portrait:");
            try {
                userDto.setPortraitInputStream(userVo.getPortrait().getInputStream());
            } catch (IOException e) {
                // mav.addObject("message", "头像文件上传失败" + e.getMessage());
                mav.setViewName("register");
                return mav;
            }
            userDto.setPortraitFilename(userVo.getPortrait().getOriginalFilename());
        }

        userDto.setPortraitUploadPath(uploadPath);

        try {
            userService.add(userDto);
        } catch (FileUploadException e) {
            mav.setViewName("register");
        }

        // mav.addObject("message", "注册成功");
        mav.setViewName("redirect:");
        return mav;
    }


    @RequestMapping(path = "/findUserByName")
    @ResponseBody
    public String findUserByName(String username) {

        String result;


        if ("".equals(username) || null == username) {
            result = "usernameIsNull";
        } else if (null == userService.findByUsername(username)) {
            result = "usernameNotExists";
        } else {
            result = "usernameExists";
        }

        return result;
    }

}