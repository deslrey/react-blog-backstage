package org.deslre.commons.entity;

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
    private String describe;

    /**
     * 更新日期
     */
    private LocalDateTime updateDate;

    /**
     * 字数
     */
    private Integer wordCount;

    /**
     * 阅读时长
     */
    private Integer readTime;

    /**
     * 类型
     */
    private String type;

    /**
     * 文章内容
     */
    private String content;

}
