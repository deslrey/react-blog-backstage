package org.deslre.user.controller;


import org.deslre.commons.result.Results;
import org.deslre.user.service.UsersService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author author
 * @since 2025-06-08
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @Resource
    private UsersService usersService;

    @PostMapping("register")
    public Results<Void> register(String email, String InviteCode) {
        return usersService.register(email, InviteCode);
    }

}
