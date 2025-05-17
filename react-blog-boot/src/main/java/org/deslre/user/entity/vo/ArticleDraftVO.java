package org.deslre.user.entity.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * ClassName: ArticleDraftVo
 * Description: ArticleDraftVO
 * Author: Deslrey
 * Date: 2025-05-17 19:22
 * Version: 1.0
 */
@Data
public class ArticleDraftVO {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 作者
     */
    private String author;

    /**
     * 图片地址
     */
    private String imagePath;

    /**
     * 文章描述
     */
    private String description;

    /**
     * 文章存储地址
     */
    private String storagePath;

    /**
     * 标签
     */
    private String tags;

    /**
     * 分类
     */
    private String category;

    /**
     * 字数
     */
    private Integer wordCount;

    /**
     * 阅读时长
     */
    private Integer readTime;

    /**
     * 创建日期
     */
    private LocalDateTime createTime;

    /**
     * 修改日期
     */
    private LocalDateTime updateTime;

    /**
     * 内容
     */
    private String content;

    /**
     * 是否启用
     */
    private Integer exist;

}
