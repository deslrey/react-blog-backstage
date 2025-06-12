package org.deslre.user.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * ClassName: InviteCodeVO
 * Description: 邀请码vo
 * Author: Deslrey
 * Date: 2025-06-12 20:36
 * Version: 1.0
 */
@Data
public class InviteCodeVO {
    private Integer id;

    /**
     * 邀请码
     */
    private String code;

    /**
     * 是否已使用
     */
    private Boolean isUsed;

    /**
     * 被谁使用（用户邮箱）
     */
    private String usedBy;

    /**
     * 使用时间
     */
    private LocalDateTime usedTime;

    /**
     * 邀请码生成者邮箱（可选）
     */
    private String createdBy;

    /**
     * 是否为管理员邀请码
     */
    private Boolean isAdmin;

    /**
     * 邀请码生成时间
     */
    private LocalDateTime createdTime;

    /**
     * 邀请码过期时间（可选）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiresTime;

    /**
     * 是否启用
     */
    private Boolean exist;

    /**
     * 备注或用途说明
     */
    private String remark;
}
