package org.deslre.user.controller;


import org.deslre.commons.result.Results;
import org.deslre.user.entity.po.Permission;
import org.deslre.user.entity.po.User;
import org.deslre.user.service.PermissionService;
import org.deslre.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author author
 * @since 2025-04-24
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private PermissionService permissionService;

    @Resource
    private UserService userService;

    @GetMapping("/login/{userId}")
    public Results<List<Permission>> login(@PathVariable Integer userId) {
        // 获取最终权限列表
        return permissionService.getFinalPermissionsByUserId(userId);
    }

    @PostMapping("login")
    public Results<User> login(String username, String password) {
        return userService.login(username, password);
    }
}
