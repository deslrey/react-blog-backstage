package org.deslre.user.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serial;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * ClassName: ArticleDraft
 * Description: 草稿
 * Author: Deslrey
 * Date: 2025-05-17 19:12
 * Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("article_draft")
public class ArticleDraft implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文章id
     */
    private Integer articleId;

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
    private Boolean exist;


}