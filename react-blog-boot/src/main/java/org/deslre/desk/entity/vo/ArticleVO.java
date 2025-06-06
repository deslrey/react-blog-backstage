package org.deslre.desk.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.deslre.commons.entity.ArticleDetail;

import java.time.LocalDateTime;

/**
 * ClassName: ArticleVO
 * Description: ArticleVO
 * Author: Deslrey
 * Date: 2025-04-10 9:50
 * Version: 1.0
 */

@Data
public class ArticleVO {

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
     * 更新日期
     */
    private LocalDateTime updateTime;

    /**
     * 访问量
     */
    private Integer pageViews;

    /**
     * 内容
     */
    private String content;
    /**
     * 是否启用
     */
    private Boolean exist;
}
