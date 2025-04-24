package org.deslre.user.controller;


import org.deslre.commons.result.Results;
import org.deslre.user.entity.Permission;
import org.deslre.user.service.PermissionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/login/{userId}")
    public Results<List<Permission>> login(@PathVariable Integer userId) {
        // 获取最终权限列表
        return permissionService.getFinalPermissionsByUserId(userId);
    }
}
