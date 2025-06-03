package org.deslre.user.entity.po;

import lombok.Data;

/**
 * ClassName: VisitorInfo
 * Description: 访客信息
 * Author: Deslrey
 * Date: 2025-06-03 11:48
 * Version: 1.0
 */
@Data
public class VisitorInfo {

    /**
     * IP
     */
    private String ip;

    /**
     * 平台
     */
    private String platform;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 设备
     */
    private String device;

    /**
     * 区域信息
     */
    private Region region;

}
