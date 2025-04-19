package org.deslre.user.entity.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * ClassName: UserVO
 * Description: 用户vo
 * Author: Deslrey
 * Date: 2025-04-18 17:00
 * Version: 1.0
 */
@Data
public class UserVO {

    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String passWord;

    /**
     * 是否为管理员
     */
    private Integer admin;

    /**
     * 是否启用
     */
    private Boolean exist;
}
