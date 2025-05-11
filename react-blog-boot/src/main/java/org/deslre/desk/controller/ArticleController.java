package org.deslre.desk.controller;

import lombok.Getter;
import org.deslre.commons.entity.ArticleDetail;
import org.deslre.commons.result.Results;
import org.deslre.desk.entity.vo.ArticleVO;
import org.deslre.desk.service.ArticleService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

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

    @PostMapping("/getArticlePage")
    public Results<List<ArticleVO>> getArticlePage(Integer page, Integer size) {
        return articleService.getArticlePage(page, size);
    }

    @PostMapping("/articleList")
    public Results<List<ArticleVO>> articleList(Boolean exist) {
        return articleService.articleList(exist);
    }

    @GetMapping("getArchives")
    public Results<List<ArticleVO>> getArchives() {
        return articleService.getArchives();
    }

    @PostMapping("saveArticle")
    public Results<String> saveArticle(
            @RequestPart("articleDetail") ArticleDetail articleDetail,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        return articleService.saveArticle(articleDetail, file);
    }

}
