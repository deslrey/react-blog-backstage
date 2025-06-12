package org.deslre.user.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: UserInfoDTO
 * Description: 用户信息传输类
 * Author: Deslrey
 * Date: 2025-06-12 19:12
 * Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO {
    private String email;
    private String userName;
    private Boolean admin;
}
