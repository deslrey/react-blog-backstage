package org.deslre.desk.service.impl;

import org.deslre.commons.result.Results;
import org.deslre.desk.service.ArticleService;
import org.deslre.vo.ArticleVO;
import org.springframework.stereotype.Service;

/**
 * ClassName: ArticleServiceImpl
 * Description: 文章服务接口实现类
 * Author: Deslrey
 * Date: 2025-03-28 13:44
 * Version: 1.0
 */

@Service
public class ArticleServiceImpl implements ArticleService {


    @Override
    public Results<ArticleVO> getArticleDetail(Integer articleId) {
        return null;
    }
}
