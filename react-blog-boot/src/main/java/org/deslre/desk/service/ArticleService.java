package org.deslre.desk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.deslre.commons.entity.ArticleDetail;
import org.deslre.commons.result.Results;
import org.deslre.desk.entity.po.Article;
import org.deslre.desk.entity.vo.ArticleVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * ClassName: ArticleService
 * Description: 文章服务类接口
 * Author: Deslrey
 * Date: 2025-03-28 13:44
 * Version: 1.0
 */
public interface ArticleService extends IService<Article> {
    Results<ArticleVO> getArticleDetail(Integer articleId);

    Results<List<ArticleVO>> getArticlePage(Integer page, Integer size);

    Results<List<ArticleVO>> getArticleList();

    Results<List<ArticleVO>> getArchives();

    Results<String> saveArticle(ArticleDetail articleDetail, MultipartFile file);
}