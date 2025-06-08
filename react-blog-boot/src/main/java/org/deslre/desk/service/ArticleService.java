package org.deslre.desk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.deslre.commons.entity.ArticleDetail;
import org.deslre.commons.result.Results;
import org.deslre.desk.entity.dto.ArticleViewDTO;
import org.deslre.desk.entity.dto.MetadataDTO;
import org.deslre.desk.entity.po.Article;
import org.deslre.desk.entity.vo.ArticleVO;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ArticleService
 * Description: 文章服务类接口
 * Author: Deslrey
 * Date: 2025-03-28 13:44
 * Version: 1.0
 */
public interface ArticleService extends IService<Article> {
    Results<ArticleVO> getArticleDetail(Integer articleId, HttpServletRequest request, @RequestHeader(value = "X-Visitor-Token", required = false) String visitorToken,
                                        @RequestHeader(value = "X-Visitor-Id", required = false) Integer visitorId);

    Results<List<ArticleVO>> getArticlePage(Integer page, Integer size);

    Results<List<ArticleVO>> getArticleList();

    Results<List<ArticleVO>> getArchives();

    Results<String> saveArticle(ArticleVO articleVO, MultipartFile file);

    Results<List<ArticleVO>> articleList();

    Results<String> updateExist(Integer id, Boolean exist);

    Results<String> articleContent(Integer id);

    Results<ArticleVO> getArticleData(Integer id);

    Results<List<ArticleViewDTO>> findTopByPageViews();

    Results<MetadataDTO> metadata(Integer articleId);
}