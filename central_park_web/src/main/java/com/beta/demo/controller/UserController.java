package com.beta.demo.controller;

import com.beta.demo.constant.UserConstant;
import com.beta.demo.dto.PortraitDto;
import com.beta.demo.dto.UserDto;
import com.beta.demo.exception.FileOversizeException;
import com.beta.demo.exception.UserLoginException;
import com.beta.demo.exception.UserModificationException;
import com.beta.demo.exception.UserRegisterException;
import com.beta.demo.pojo.User;
import com.beta.demo.service.UserService;
import com.beta.demo.vo.PasswordVo;
import com.beta.demo.vo.UserLessVo;
import com.beta.demo.vo.UserVo;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
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


    @RequestMapping("/registerPage")
    public String registerPage(){
        return "register";
    }


    /**
     * 进行用户的注册
     *
     * @param userVo  接收表单的数据
     * @param session
     * @return
     */
    @RequestMapping(path = "/registerNewUser", method = RequestMethod.POST)
    public ModelAndView registNewUser(@ModelAttribute UserVo userVo, HttpSession session) {

        ModelAndView mav = new ModelAndView();
        String uploadPath = session.getServletContext().getRealPath(UserConstant.PORTRAIT_UPLOAD_PATH);


        // validate the form elements
        Pattern pattern = Pattern.compile("[\\w]{2,15}");
        Matcher matcher = pattern.matcher(userVo.getUsername());
        if (!matcher.matches()) {
            mav.addObject("failTitle", "注册失败");
            mav.addObject("message", "用户名不符合规则");
            mav.setViewName("fail-info");
            return mav;
        }


        // FIXME : 这里的密码已经被加密了，所以无法进行准确的二次验证
        pattern = Pattern.compile("[\\w]{32}");
        matcher = pattern.matcher(userVo.getPassword());
        if (!matcher.matches()) {
            mav.addObject("failTitle", "注册失败");
            mav.addObject("message", "密码不符合规则");
            mav.setViewName("fail-info");
            return mav;
        }



        if (ObjectUtils.isEmpty(userVo.getEmail())) {
            userVo.setEmail(null);
        } else {
            pattern = Pattern.compile("[\\da-z]+([\\-\\.\\_]?[\\da-z]+)*@[\\da-z]+([\\-\\.]?[\\da-z]+)*(\\.[a-z]{2,})+");
            matcher = pattern.matcher(userVo.getEmail());
            if (!matcher.matches()) {
                mav.addObject("failTitle", "注册失败");
                mav.addObject("message", "邮箱不符合规则");
                mav.setViewName("fail-info");
                return mav;
            }
        }

        // 你要绕过js检查搞我，我就默认你是男的
        if (ObjectUtils.isEmpty(userVo.getGender())) {
            userVo.setGender(true);
        }


        // transfer VO to DTO
        UserDto userDto = new UserDto();
        // get from default
        userDto.setId(UUID.randomUUID().toString());
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
                System.out.println("文件大小：" + userVo.getPortrait().getSize());
                if (userVo.getPortrait().getSize() > UserConstant.PORTRAIT_MAX_SIZE) {
                    throw new FileOversizeException("文件内容过大，不应超过3M");
                }
                userDto.setPortraitInputStream(userVo.getPortrait().getInputStream());
            } catch (IOException | FileOversizeException e) {
                mav.addObject("failTitle", "注册失败");
                mav.addObject("message", "头像文件上传失败" + e.getMessage());
                mav.setViewName("fail-info");
                return mav;
            }
            userDto.setPortraitFilename(userVo.getPortrait().getOriginalFilename());
        }

        userDto.setPortraitUploadPath(uploadPath);

        try {
            userService.register(userDto);
        } catch (FileUploadException | UserRegisterException e) {
            mav.addObject("failTitle", "注册失败");
            mav.addObject("message", e.getMessage());
            mav.setViewName("fail-info");
            return mav;
        }

        mav.addObject("failTitle", "注册成功");
        mav.setViewName("fail-info");
        return mav;
    }


    /**
     * 登录用户，跳转到chatroom.jsp中
     *
     * @param userVo
     * @param session
     * @return
     */
    @RequestMapping(path = "/chatroom", method = RequestMethod.POST)
    public ModelAndView loginUser(@ModelAttribute UserVo userVo, HttpSession session) {
        ModelAndView mav = new ModelAndView();

        User user = null;

        System.out.println("username:" + userVo.getUsername());
        System.out.println("password:" + userVo.getPassword());

        try {
            user = userService.login(userVo.getUsername(), userVo.getPassword());
        } catch (UserLoginException e) {
            mav.addObject("failTitle", "登录失败");
            mav.addObject("message", e.getMessage());
            mav.setViewName("fail-info");
            return mav;
        }

        UserLessVo userLessVo = new UserLessVo();
        userLessVo.setId(user.getId());
        userLessVo.setUsername(user.getUsername());
        userLessVo.setPortrait(user.getPortrait());
        userLessVo.setGender(user.isGender());
        userLessVo.setEmail(user.getEmail());

        // 向自己的session中添加user信息
        session.setAttribute("selfUser", userLessVo);

        mav.setViewName("chatroom");
        return mav;
    }


    /**
     * 在注册时使用ajax调用，查看用户名是否在数据库中已存在
     *
     * @param username
     * @return
     */
    @RequestMapping(path = "/findIfUserExists", method = RequestMethod.POST)
    @ResponseBody
    public String findIfUserExists(String username) {

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


    /**
     * 查看其他用户的信息
     *
     * @param username
     * @param httpSession
     * @return
     */
    @RequestMapping("/userInfo")
    public ModelAndView checkUserInfo(String username, HttpSession httpSession) {

        System.out.println("checkUserInfo 中 获取的 username值：" + username);

        ModelAndView mav = new ModelAndView();


        // 检查登录状态
        UserLessVo userLessVo = (UserLessVo) httpSession.getAttribute("selfUser");
        if (ObjectUtils.isEmpty(userLessVo)) { // session中没有用户信息，即用户未登录，不予查看
            System.out.println("用户名为空!");
            mav.addObject("failTitle", "查询失败");
            mav.addObject("message", "用户尚未登录");
            mav.setViewName("fail-info");
            return mav;
        } else {
            User u = userService.findById(userLessVo.getId());
            if (ObjectUtils.isEmpty(u)) { // 未找到ID对应的用户
                System.out.println("用户登录信息有误!");
                mav.addObject("failTitle", "查询失败");
                mav.addObject("message", "用户登录信息有误");
                mav.setViewName("fail-info");
                return mav;
            } else { // 找到了ID对应的用户
                if (username.equals(userLessVo.getUsername())) { // 且此用户ID是自己，则返回修改信息页面
                    mav.addObject("userInfo", userLessVo);
                    mav.setViewName("selfUser");
                    return mav;
                }
            }
        }

        // 查看其他用户信息：将查找出来的User对象转换成UserLessVo
        User otherUser = userService.findByUsername(username);

        if (ObjectUtils.isEmpty(otherUser)) {
            mav.addObject("failTitle", "查询失败");
            mav.addObject("message", "查询参数有误");
            mav.setViewName("fail-info");
            return mav;
        }

        UserLessVo userInfo = new UserLessVo(); // 返回的UserLessVo对象

        userInfo.setId(otherUser.getId());
        userInfo.setUsername(otherUser.getUsername());
        userInfo.setPortrait(otherUser.getPortrait());
        userInfo.setGender(otherUser.isGender());
        userInfo.setEmail(otherUser.getEmail());

        mav.addObject("userInfo", userInfo);
        mav.setViewName("otherUser");

        return mav;

    }


    @RequestMapping(value = "/modifyBasicInfo", method = RequestMethod.POST)
    public ModelAndView modifySelfUser(@ModelAttribute UserVo userVo, HttpSession httpSession) {
        // userVo 传入的新用户信息
        ModelAndView mav = new ModelAndView();

        // 登录的用户信息
        UserLessVo userLessVo = (UserLessVo) httpSession.getAttribute("selfUser");
        checkHttpSessionUser(userLessVo);

        Pattern pattern;
        Matcher matcher;
        // 查看用户名是否更改，且赋值给DTO
        String voUsername = userVo.getUsername();
        if (!voUsername.equals(userLessVo.getUsername())) { // 在用户名发生更改的情况下
            pattern = Pattern.compile("[\\w]{2,15}");
            matcher = pattern.matcher(voUsername);
            if (!matcher.matches()) { // 用户名不符合规则
                mav.addObject("failTitle", "修改失败");
                mav.addObject("message", "用户名不符合规则");
                mav.setViewName("fail-info");
                return mav;
            } else if (!ObjectUtils.isEmpty(userService.findByUsername(voUsername))) { // 用户名符合规则但已存在
                mav.addObject("failTitle", "修改失败");
                mav.addObject("message", "用户名已存在");
                mav.setViewName("fail-info");
                return mav;
            }
        }


        // 你要绕过js检查搞我，我就默认你是男的
        boolean voGender = userVo.getGender();
        if (ObjectUtils.isEmpty(voGender)) {
            userVo.setGender(true);
        }


        // 将邮箱赋值给DTO
        String voEmail = userVo.getEmail();
        if (ObjectUtils.isEmpty(voEmail)) { // 若邮箱为空则空
            userVo.setEmail(null);
        } else {
            pattern = Pattern.compile("[\\da-z]+([\\-\\.\\_]?[\\da-z]+)*@[\\da-z]+([\\-\\.]?[\\da-z]+)*(\\.[a-z]{2,})+");
            matcher = pattern.matcher(voEmail);
            if (!matcher.matches()) { // 若邮箱有值且不符合规则
                mav.addObject("failTitle", "修改失败");
                mav.addObject("message", "邮箱不符合规则");
                mav.setViewName("fail-info");
                return mav;
            }
        }

        User user = new User();
        user.setId(userLessVo.getId());
        user.setUsername(userVo.getUsername());
        user.setGender(userVo.getGender());
        user.setEmail(userVo.getEmail());

        try {
            userService.modifyBasicInfo(user);
        } catch (UserModificationException e) {
            mav.addObject("failTitle", "修改失败");
            mav.addObject("message", e.getMessage());
            mav.setViewName("fail-info");
            return mav;
        }

        // 更改httpSession中的UserLessVo值
        userLessVo.setUsername(userVo.getUsername());
        userLessVo.setGender(userVo.getGender());
        userLessVo.setEmail(userVo.getEmail());

        httpSession.setAttribute("selfUser",userLessVo);


        mav.addObject("userInfo",userLessVo);
        mav.setViewName("selfUser");
        return mav;
    }


    /**
     * 更改用户密码
     * @param passwordVo
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
    public  ModelAndView modifyPassword(@ModelAttribute PasswordVo passwordVo, HttpSession httpSession){
        System.out.println("modifyPassword开始执行");
        // passwordVo 传入的密码信息
        ModelAndView mav = new ModelAndView();

        // 登录的用户信息
        UserLessVo userLessVo = (UserLessVo) httpSession.getAttribute("selfUser");
        checkHttpSessionUser(userLessVo);

        // 判断原密码是否正确
        if (!passwordVo.getOldPassword().equals(userService.findPasswordById(userLessVo.getId()))){
            System.out.println("数据库中密码：" + userService.findPasswordById(userLessVo.getId()));
            System.out.println("提交的原密码：" + passwordVo.getOldPassword());
            mav.addObject("failTitle", "修改失败");
            mav.addObject("message", "原密码错误");
            mav.setViewName("fail-info");
            return mav;
        }else { // 若正确，则开始修改密码，我也不帮你判空了，随你
            try {
                userService.modifyPassword(userLessVo.getId(), passwordVo.getPassword());
                mav.addObject("failTitle", "修改成功");
                mav.addObject("message", "请重新登录");
                mav.setViewName("fail-info");
                return mav;
            } catch (UserModificationException e) {
                mav.addObject("failTitle", "修改失败");
                mav.addObject("message", e.getMessage());
                mav.setViewName("fail-info");
                return mav;
            }
        }
    }


    @RequestMapping("/modifyPortrait")
    public ModelAndView modifyPortrait(@RequestParam CommonsMultipartFile portrait, HttpSession httpSession){
        ModelAndView mav = new ModelAndView();
        String uploadPath = httpSession.getServletContext().getRealPath(UserConstant.PORTRAIT_UPLOAD_PATH);

        // 登录的用户信息
        UserLessVo userLessVo = (UserLessVo) httpSession.getAttribute("selfUser");
        checkHttpSessionUser(userLessVo);

        // 用于数据传输的PortraitDto
        PortraitDto portraitDto = new PortraitDto();


        if (ObjectUtils.isEmpty(portrait)){
            mav.addObject("failTitle", "修改失败");
            mav.addObject("message", "图片接收错误");
            mav.setViewName("fail-info");
            return mav;
        }else { // 非空
            try {
                System.out.println("文件大小：" + portrait.getSize());
                if (portrait.getSize() > UserConstant.PORTRAIT_MAX_SIZE) {
                    throw new FileOversizeException("文件内容过大，不应超过3M");
                }
                portraitDto.setInputStream(portrait.getInputStream());
                portraitDto.setFilename(portrait.getOriginalFilename());
                portraitDto.setUploadPath(uploadPath);

                userService.modifyPortrait(userLessVo.getId(), portraitDto);
            } catch (IOException | FileOversizeException | UserModificationException | FileUploadException e) {
                mav.addObject("failTitle", "修改失败");
                mav.addObject("message", "头像文件上传失败：" + e.getMessage());
                mav.setViewName("fail-info");
                return mav;
            }


            mav.addObject("failTitle", "修改成功");
            mav.addObject("message", "请重新登录");
            mav.setViewName("fail-info");
            return mav;

        }

    }

    /**
     * 传入一个UserLessVo，判断是否空和正确性，若未通过检查，返回指定的ModelAndView
     *
     * @param userLessVo
     * @return
     */
    private ModelAndView checkHttpSessionUser(UserLessVo userLessVo) {
        ModelAndView mav = new ModelAndView();

        if (ObjectUtils.isEmpty(userLessVo)) {
            System.out.println("用户名为空!");
            mav.addObject("failTitle", "查询失败");
            mav.addObject("message", "用户尚未登录");
            mav.setViewName("fail-info");
            return mav;
        } else {
            User u = userService.findById(userLessVo.getId());
            if (ObjectUtils.isEmpty(u)) {
                System.out.println("用户登录信息有误!");
                mav.addObject("failTitle", "查询失败");
                mav.addObject("message", "用户登录信息有误");
                mav.setViewName("fail-info");
                return mav;
            }
        }

        return null;
    }
}
