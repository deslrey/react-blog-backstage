package org.deslre.desk.controller;

import lombok.Getter;
import org.deslre.commons.result.Results;
import org.deslre.desk.entity.vo.ArticleVO;
import org.deslre.desk.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * ClassName: ArticleController
 * Description: 文章相关控制器
 * Author: Deslrey
 * Date: 2025-03-28 13:43
 * Version: 1.0
 */

@CrossOrigin
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @GetMapping("title")
    public Results<String> getTitle() {
        return Results.ok("测试");
    }

    @PostMapping("getArticleDetail")
    public Results<ArticleVO> getArticleDetail(Integer articleId) {
        return articleService.getArticleDetail(articleId);
    }


}
