package org.deslre.desk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.deslre.commons.entity.ArticleDetail;
import org.deslre.commons.result.ResultCodeEnum;
import org.deslre.commons.result.Results;
import org.deslre.commons.utils.FileReaderUtil;
import org.deslre.commons.utils.NumberUtils;
import org.deslre.commons.utils.StringUtils;
import org.deslre.desk.convert.ArticleConvert;
import org.deslre.desk.entity.po.Article;
import org.deslre.desk.entity.vo.ArticleVO;
import org.deslre.desk.mapper.ArticleMapper;
import org.deslre.desk.service.ArticleService;
import org.deslre.exception.DeslreException;
import org.springframework.stereotype.Service;

/**
 * ClassName: ArticleServiceImpl
 * Description: 文章服务接口实现类
 * Author: Deslrey
 * Date: 2025-03-28 13:44
 * Version: 1.0
 */

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public Results<ArticleVO> getArticleDetail(Integer articleId) {
        if (articleId == null || articleId <= 0) {
            throw new DeslreException(ResultCodeEnum.CODE_501);
        }

        Article article = getArticleDetail(articleId, true);
        if (article == null) {
            throw new DeslreException("查找失败");
        }
        if (StringUtils.isEmpty(article.getStoragePath())) {
            throw new DeslreException("查找文章存储地址不存在");
        }

        ArticleDetail articleDetail = FileReaderUtil.parse(article.getStoragePath());
        if (articleDetail == null) {
            throw new DeslreException("当前文章不存在");
        }

        ArticleVO articleVO = ArticleConvert.INSTANCE.convertVO(article);
        articleVO.setArticleDetail(articleDetail);

        return Results.ok(articleVO);
    }


    private Article getArticleDetail(Integer id, boolean exist) {
        if (NumberUtils.isLessThanZero(id)) {
            return new Article();
        }
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>().eq(Article::getId, id).eq(Article::getExist, exist);
        return getOne(queryWrapper);
    }
}
