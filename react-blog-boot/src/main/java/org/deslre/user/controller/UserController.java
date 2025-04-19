package org.deslre.user.controller;


import org.deslre.commons.result.Results;
import org.deslre.user.entity.vo.UserVO;
import org.deslre.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author author
 * @since 2025-04-18
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("register")
    public Results<UserVO> register(String userName, String passWord) {
        return userService.register(userName, passWord);
    }

    @PostMapping("userLogin")
    public Results<UserVO> userLogin(String userName, String passWord) {
        return userService.userLogin(userName, passWord);
    }


}
