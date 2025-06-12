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
 * ClassName: AdminOperationLog
 * Description: TODO
 * Author: Deslrey
 * Date: 2025-06-11 9:12
 * Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("admin_operation_log")
public class AdminOperationLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String operatorEmail;

    private String operatorUser;

    private String operatorName;

    /**
     * 操作描述
     */
    private String operation;

    private String operationIp;

    private String platform;

    private String browser;

    private String device;

    private String country;

    private String province;

    private String city;

    /**
     * 操作时间
     */
    private LocalDateTime operationTime;

    private Boolean exist;
}