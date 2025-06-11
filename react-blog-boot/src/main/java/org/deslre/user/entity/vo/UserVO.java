package org.deslre.user.entity.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * ClassName: UserVO
 * Description: 用户信息返回类
 * Author: Deslrey
 * Date: 2025-06-10 10:21
 * Version: 1.0
 */
@Data
public class UserVO {

    private String userName;

    private String email;

    private String image;

    private Boolean admin;

}
