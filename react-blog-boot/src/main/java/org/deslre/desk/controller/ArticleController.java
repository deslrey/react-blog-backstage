package org.deslre.desk.controller;

import org.deslre.commons.result.Results;
import org.deslre.desk.service.ArticleService;
import org.deslre.vo.ArticleVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * ClassName: ArticleController
 * Description: 文章相关控制器
 * Author: Deslrey
 * Date: 2025-03-28 13:43
 * Version: 1.0
 */

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @PostMapping("articleId")
    public Results<ArticleVO> getArticleDetail(Integer articleId) {
        return articleService.getArticleDetail(articleId);
    }


}
