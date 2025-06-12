package org.deslre.desk.entity.po;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * ClassName: ArticleDetail
 * Description: 文章细节
 * Author: Deslrey
 * Date: 2025-04-12 16:41
 * Version: 1.0
 */

@Data
public class ArticleDetail {

    /**
     * id
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 作者
     */
    private String author;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建日期
     */
    private LocalDateTime createTime;

    /**
     * 更新日期
     */
    private LocalDateTime updateTime;

    /**
     * 字数
     */
    private Integer wordCount;

    /**
     * 阅读时长
     */
    private Integer readTime;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 是否启用
     */
    private Boolean exist;

}
