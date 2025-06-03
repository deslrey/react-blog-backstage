package org.deslre.desk.entity.dto;

import lombok.Data;

/**
 * ClassName: ArticleViewDTO
 * Description: 文章访问量
 * Author: Deslrey
 * Date: 2025-06-03 22:17
 * Version: 1.0
 */
@Data
public class ArticleViewDTO {
    private String title;
    private Integer pageViews;
}