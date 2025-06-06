package org.deslre.user.entity.po;

import lombok.Data;

/**
 * ClassName: VisitCount
 * Description: 访问统计类
 * Author: Deslrey
 * Date: 2025-06-04 9:04
 * Version: 1.0
 */
@Data
public class VisitCount {
    private String title;
    private Integer count;

    public VisitCount(String title, Integer count) {
        this.title = title;
        this.count = count;
    }
}
