package org.deslre.user.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ClassName: InvitationCodes
 * Description: 邀请码实体类
 * Author: Deslrey
 * Date: 2025-06-08 20:06
 * Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("invitation_codes")
public class InvitationCodes implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
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
