package org.deslre.desk.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * ClassName: ArticleSearchDTO
 * Description: TODO
 * Author: Deslrey
 * Date: 2025-06-08 14:55
 * Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleSearchDTO {
    private Integer id;
    private String title;
    private String author;
    private LocalDateTime updateTime;
    private String snippet;
}
