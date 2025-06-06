package org.deslre.user.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serial;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * ClassName: VisitLogVO
 * Description: 访问日志VO
 * Author: Deslrey
 * Date: 2025-06-03 10:27
 * Version: 1.0
 */
@Data
public class VisitLogVO {

    private Long id;

    private String visitorIp;

    private Long articleId;

    private String platform;

    private String browser;

    private String device;

    private String country;

    private String province;

    private String city;

    private LocalDateTime visitTime;

    private LocalDate visitDate;

    private String description;

    private Boolean exist;

}
