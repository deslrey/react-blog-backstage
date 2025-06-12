package org.deslre.desk.controller;

import org.deslre.commons.result.Results;
import org.deslre.desk.entity.dto.ArticleViewDTO;
import org.deslre.desk.entity.dto.MetadataDTO;
import org.deslre.desk.entity.vo.ArticleVO;
import org.deslre.desk.service.ArticleService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    public Results<ArticleVO> getArticleDetail(Integer articleId, HttpServletRequest request, @RequestHeader(value = "X-Visitor-Token", required = false) String visitorToken,
                                               @RequestHeader(value = "X-Visitor-Id", required = false) Integer visitorId) {
        return articleService.getArticleDetail(articleId, request, visitorToken, visitorId);
    }

    @PostMapping("/getArticlePage")
    public Results<List<ArticleVO>> getArticlePage(Integer page, Integer size) {
        return articleService.getArticlePage(page, size);
    }

    @GetMapping("/getArticleList")
    public Results<List<ArticleVO>> getArticleList() {
        return articleService.getArticleList();
    }

    @GetMapping("articleList")
    public Results<List<ArticleVO>> articleList() {
        return articleService.articleList();
    }

    @GetMapping("getArchives")
    public Results<List<ArticleVO>> getArchives() {
        return articleService.getArchives();
    }

    @PostMapping("saveArticle")
    public Results<String> saveArticle(
            @RequestPart("ArticleVO") ArticleVO articleVO,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        return articleService.saveArticle(articleVO, file);
    }

    @PostMapping("updateExist")
    public Results<String> updateExist(Integer id, Boolean exist) {
        return articleService.updateExist(id, exist);
    }

    @PostMapping("articleContent")
    public Results<String> articleContent(Integer id) {
        return articleService.articleContent(id);
    }

    @PostMapping("getArticleData")
    public Results<ArticleVO> getArticleData(Integer id) {
        return articleService.getArticleData(id);
    }

    @GetMapping("findTopByPageViews")
    public Results<List<ArticleViewDTO>> findTopByPageViews() {
        return articleService.findTopByPageViews();
    }

    @PostMapping("metadata")
    public Results<MetadataDTO> metadata(Integer articleId) {
        return articleService.metadata(articleId);
    }
}
