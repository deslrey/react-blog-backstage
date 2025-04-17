package org.deslre.desk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.deslre.commons.entity.ArticleDetail;
import org.deslre.commons.result.ResultCodeEnum;
import org.deslre.commons.result.Results;
import org.deslre.commons.utils.FileReaderUtil;
import org.deslre.commons.utils.NumberUtils;
import org.deslre.commons.utils.StaticUtil;
import org.deslre.commons.utils.StringUtils;
import org.deslre.desk.convert.ArticleConvert;
import org.deslre.desk.entity.po.Article;
import org.deslre.desk.entity.vo.ArticleVO;
import org.deslre.desk.mapper.ArticleMapper;
import org.deslre.desk.service.ArticleService;
import org.deslre.exception.DeslreException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        Article article = getArticleDetail(articleId, StaticUtil.TRUE);
        if (article == null) {
            throw new DeslreException("查找失败");
        }
        if (StringUtils.isEmpty(article.getStoragePath())) {
            throw new DeslreException("查找文章存储地址不存在");
        }

        String markdownFile = FileReaderUtil.readMarkdownFile(article.getStoragePath());
        if (StringUtils.isEmpty(markdownFile)) {
            throw new DeslreException("当前文章不存在");
        }

        ArticleVO articleVO = ArticleConvert.INSTANCE.convertVO(article);
        articleVO.setContent(markdownFile);

        return Results.ok(articleVO);
    }


    @Override
    public Results<List<ArticleVO>> getArticlePage(Integer page, Integer size) {
        if (NumberUtils.isLessThanZero(page) || NumberUtils.isLessThanZero(size)) {
            throw new DeslreException("获取文章列表失败");
        }

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>().eq(Article::getExist, StaticUtil.TRUE);
        Page<Article> articlePage = baseMapper.selectPage(new Page<>(page, size), queryWrapper);
        List<Article> articleList = articlePage.getRecords();
        if (articleList == null || articleList.isEmpty()) {
            throw new DeslreException("查看文章列表失败");
        }
        List<ArticleVO> convertList = ArticleConvert.INSTANCE.convertList(articleList);
        return Results.ok(convertList);
    }

    @Override
    public Results<List<ArticleVO>> getArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>().eq(Article::getExist, StaticUtil.TRUE);
        List<Article> articleList = list(queryWrapper);
        if (articleList == null || articleList.isEmpty()) {
            throw new DeslreException("查看文章列表失败");
        }
        List<ArticleVO> convertList = ArticleConvert.INSTANCE.convertList(articleList);
        return Results.ok(convertList);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Results<List<ArticleVO>> getArchives() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>().eq(Article::getExist, StaticUtil.TRUE).select(Article::getId, Article::getTitle, Article::getCreateTime);
        List<Article> articleList = list(queryWrapper);
        if (articleList == null || articleList.isEmpty()) {
            return Results.ok(new ArrayList<>());
        }

        List<ArticleVO> convertList = ArticleConvert.INSTANCE.convertList(articleList);
        return Results.ok(convertList);
    }

    private Article getArticleDetail(Integer id, boolean exist) {
        if (NumberUtils.isLessThanZero(id)) {
            return new Article();
        }
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>().eq(Article::getId, id).eq(Article::getExist, exist);
        return getOne(queryWrapper);
    }
}
