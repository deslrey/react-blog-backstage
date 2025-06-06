package org.deslre.user.entity.po;

import lombok.Data;

/**
 * ClassName: DailyVisitCount
 * Description: TODO
 * Author: Deslrey
 * Date: 2025-06-04 9:04
 * Version: 1.0
 */
@Data
public class DailyVisitCount {
    private String date; // yyyy-MM-dd 格式
    private Integer count;

    public DailyVisitCount(String date, Integer count) {
        this.date = date;
        this.count = count;
    }
}
