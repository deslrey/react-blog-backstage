package org.deslre.desk.service;

import org.deslre.commons.result.Results;
import org.deslre.vo.ArticleVO;

/**
 * ClassName: ArticleService
 * Description: 文章服务类接口
 * Author: Deslrey
 * Date: 2025-03-28 13:44
 * Version: 1.0
 */
public interface ArticleService {
    Results<ArticleVO> getArticleDetail(Integer articleId);
}
