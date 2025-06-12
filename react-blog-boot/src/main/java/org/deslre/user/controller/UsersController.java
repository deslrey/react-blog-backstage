package org.deslre.user.controller;


import org.deslre.annotation.AuthCheck;
import org.deslre.result.Results;
import org.deslre.exception.DeslreException;
import org.deslre.user.entity.po.User;
import org.deslre.user.entity.vo.UserVO;
import org.deslre.user.service.UsersService;
import org.deslre.utils.Constants;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author author
 * @since 2025-06-08
 */
@RestController
@RequestMapping("/user")
public class UsersController {

    @Resource
    private UsersService usersService;

    @PostMapping("register")
    public Results<Void> register(HttpSession session, String checkCode, String userName, String email, String passWord, String inviteCode) {
        try {
            if (!checkCode.equalsIgnoreCase((String) session.getAttribute(Constants.CHECK_CODE_KEY))) {
                throw new DeslreException("图片验证码不正确");
            }
            return usersService.register(userName, email, passWord, inviteCode);
        } finally {
            session.removeAttribute(Constants.CHECK_CODE_KEY);
        }
    }

    @PostMapping("login")
    public Results<UserVO> login(HttpSession session, String email, String passWord, String checkCode) {
        try {
            if (!checkCode.equalsIgnoreCase((String) session.getAttribute(Constants.CHECK_CODE_KEY))) {
                throw new DeslreException("图片验证码不正确");
            }
            Results<UserVO> vo = usersService.login(email, passWord);
            session.setAttribute(Constants.SESSION_KEY, vo.getData());
            return vo;
        } finally {
            session.removeAttribute(Constants.CHECK_CODE_KEY);
        }
    }

    @PostMapping("block")
    @AuthCheck(admin = true, checkLogin = true, log = "禁用/开启用户", category = "user")
    public Results<Void> block(HttpServletRequest request, Integer userId, String email, Boolean exist) {
        return usersService.block(userId, email, exist);
    }

    @PostMapping("sendEmailCode")
    public Results<Void> sendEmailCode(String email) {
        return usersService.sendEmailCode(email);
    }

    @PostMapping("updatePassWord")
    @AuthCheck(admin = false, checkLogin = true, log = "修改密码", category = "user")
    public Results<UserVO> updatePassWord(HttpServletRequest request, String email, String oldPassWord, String newPassWord) {
        return usersService.updatePassWord(email, oldPassWord, newPassWord);
    }

    @PostMapping("updateUserName")
    @AuthCheck(admin = false, checkLogin = true, log = "修改用户名", category = "user")
    public Results<UserVO> updateUserName(HttpServletRequest request, String email, String userName) {
        return usersService.updateUserName(email, userName);
    }

    @PostMapping("updateAvatar")
    @AuthCheck(admin = false, checkLogin = true, log = "修改头像", category = "user")
    public Results<UserVO> updateAvatar(HttpServletRequest request, String email, @RequestParam("file") MultipartFile avatarFile) {
        return usersService.updateAvatar(email, avatarFile);
    }

    @PostMapping("checkUserInfo")
    @AuthCheck(admin = false, checkLogin = true, log = "校验用户信息", category = "user")
    public Results<UserVO> checkUserInfo(String email, Boolean admin) {
        return usersService.checkUserInfo(email, admin);
    }

    @GetMapping("userList")
    @AuthCheck(admin = true, checkLogin = true, log = "获取用户列表", category = "user")
    public Results<List<User>> userList(HttpServletRequest request) {
        return usersService.userList();
    }

}
