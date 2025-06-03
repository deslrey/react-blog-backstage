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
 * ClassName: Visitor
 * Description: 访客信息
 * Author: Deslrey
 * Date: 2025-06-02 22:34
 * Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("visitor")
public class Visitor implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String ip;

    private String platform;

    private String browser;

    private String device;

    private String province;

    private String city;

    private Integer visitCount;

    private LocalDateTime firstVisit;

    private LocalDateTime lastVisit;

    private String visitorToken;
}
