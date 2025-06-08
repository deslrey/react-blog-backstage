package org.deslre.desk.controller;

import org.deslre.commons.result.Results;
import org.deslre.desk.entity.dto.ArticleSearchDTO;
import org.deslre.desk.service.ArticleSearchService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * ClassName: SearchController
 * Description: 文章搜索控制层
 * Author: Deslrey
 * Date: 2025-06-08 16:16
 * Version: 1.0
 */
@RestController
@RequestMapping("search")
public class SearchController {

    @Resource
    private ArticleSearchService articleSearchService;

    @PostMapping("searchArticle")
    public Results<List<ArticleSearchDTO>> searchArticle(String keyWord) {
        return articleSearchService.searchArticles(keyWord, 0, 20);
    }

}
