package org.deslre.user.controller;

import org.deslre.user.entity.dto.UserInfoDTO;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName: BaseController
 * Description: 顶层controller
 * Author: Deslrey
 * Date: 2025-06-12 19:13
 * Version: 1.0
 */
@RestController
public class BaseController {

    /**
     * 从请求头中解析用户信息
     *
     * @param request HttpServletRequest 请求对象
     */
    protected UserInfoDTO parseUserInfo(HttpServletRequest request) {
        String email = request.getHeader("x-deslre-email");
        String adminStr = request.getHeader("x-deslre-admin");
        String userName = request.getHeader("x-deslre-userName");
        boolean admin = "true".equalsIgnoreCase(adminStr) || "1".equals(adminStr);

        System.out.println("前端传来的用户信息: email=" + email + ", admin=" + admin + ", userName=" + userName);
        return new UserInfoDTO(email, userName, admin);
    }
}

