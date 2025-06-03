package org.deslre.user.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * ClassName: VisitorVO
 * Description: 访客信息VO
 * Author: Deslrey
 * Date: 2025-06-03 10:24
 * Version: 1.0
 */
@Data
public class VisitorVO {

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

    private Boolean isBlock;
}
