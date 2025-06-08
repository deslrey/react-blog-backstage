package org.deslre.desk.service;

import org.deslre.commons.result.Results;
import org.deslre.desk.entity.dto.ArticleSearchDTO;

import java.util.List;

/**
 * ClassName: ArticleSearchService
 * Description: 文章内容搜索
 * Author: Deslrey
 * Date: 2025-06-08 15:16
 * Version: 1.0
 */
public interface ArticleSearchService {
    Results<List<ArticleSearchDTO>> searchArticles(String keyword, int page, int size);
}
