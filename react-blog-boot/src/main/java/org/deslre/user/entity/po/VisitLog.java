package org.deslre.user.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * ClassName: VisitLog
 * Description: 访问日志
 * Author: Deslrey
 * Date: 2025-06-02 22:30
 * Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("visit_log")
public class VisitLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String visitorIp;

    private Long articleId;

    private String platform;

    private String browser;

    private String device;

    private String province;

    private String city;

    private LocalDateTime visitTime;

    private LocalDate visitDate;

    private String description;
}
