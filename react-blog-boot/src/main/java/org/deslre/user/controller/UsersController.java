package org.deslre.user.controller;


import org.deslre.commons.result.Results;
import org.deslre.exception.DeslreException;
import org.deslre.user.entity.vo.UserVO;
import org.deslre.user.service.UsersService;
import org.deslre.utils.Constants;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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
    public Results<Void> block(Integer userId, Boolean exist) {
        return usersService.block(userId, exist);
    }

    @PostMapping("sendEmailCode")
    public Results<Void> sendEmailCode(String email) {
        return usersService.sendEmailCode(email);
    }
}
