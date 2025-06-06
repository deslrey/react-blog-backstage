package org.deslre.user.entity.po;

import lombok.Data;

/**
 * ClassName: Region
 * Description: 地区
 * Author: Deslrey
 * Date: 2025-06-03 11:48
 * Version: 1.0
 */
@Data
public class Region {
    /**
     * 国家
     */
    private String country;
    /**
     * 城市
     */
    private String city;
    /**
     * 注册地
     */
    private String province;

}
