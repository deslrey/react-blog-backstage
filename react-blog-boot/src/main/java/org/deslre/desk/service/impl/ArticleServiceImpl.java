package org.deslre.desk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.deslre.commons.entity.ArticleDetail;
import org.deslre.commons.result.ResultCodeEnum;
import org.deslre.commons.result.Results;
import org.deslre.commons.utils.*;
import org.deslre.desk.convert.ArticleConvert;
import org.deslre.desk.entity.po.Article;
import org.deslre.desk.entity.vo.ArticleVO;
import org.deslre.desk.mapper.ArticleMapper;
import org.deslre.desk.service.ArticleService;
import org.deslre.exception.DeslreException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * ClassName: ArticleServiceImpl
 * Description: 文章服务接口实现类
 * Author: Deslrey
 * Date: 2025-03-28 13:44
 * Version: 1.0
 */

@Slf4j
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
//        String markdownFile = FileReaderUtil.readMarkdownFile("E:\\md\\demo.md");
        if (StringUtils.isEmpty(markdownFile)) {
            throw new DeslreException("当前文章不存在");
        }

        List<String> extractedImagePaths = FileReaderUtil.extractImagePaths(markdownFile);
        for (String imagePath : extractedImagePaths) {
            markdownFile = markdownFile.replace(imagePath, "http://localhost:8080/deslre/images/ikun.jpg");
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
    public Results<List<ArticleVO>> articleList() {
        List<Article> articleList = list();
        if (articleList == null) {
            return Results.ok("暂无数据");
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

    @Override
    public Results<String> saveArticle(ArticleVO articleVO, MultipartFile file) {
        if (articleVO == null) {
            return Results.fail(ResultCodeEnum.DATA_ERROR);
        }

        Article article = ArticleConvert.INSTANCE.convert(articleVO);
        if (StringUtils.isEmpty(article.getTitle())) {
            article.setTitle(StaticUtil.DEFAULT_TITLE);
        }
        if (StringUtils.isEmpty(article.getAuthor())) {
            article.setAuthor(StaticUtil.DEFAULT_AUTHOR);
        }
        if (StringUtils.isEmpty(article.getDescription())) {
            article.setDescription(StaticUtil.DEFAULT_DESCRIPTION);
        }
        if (article.getCreateTime() == null) {
            article.setCreateTime(LocalDateTime.now());
        }
        article.setUpdateTime(LocalDateTime.now());
        if (file == null || file.isEmpty()) {
            if (StringUtils.isEmpty(article.getImagePath())) {
                article.setImagePath(StaticUtil.DEFAULT_COVER);
            }
        } else {
            try {
                String currentYear = DateUtil.getCurrentYear();
                String currentMonth = DateUtil.getCurrentMonth();
                String currentDay = DateUtil.getCurrentDay();

                // 构建目录路径
                String relativePath = currentYear + File.separator + currentMonth + File.separator;
                String fullDirPath = StaticUtil.RESOURCE_IMAGE + relativePath;

                File dir = new File(fullDirPath);
                if (!dir.exists()) {
                    boolean created = dir.mkdirs(); // 创建多级目录
                    if (!created) {
                        log.error("目录创建失败: " + fullDirPath);
                        return Results.fail(ResultCodeEnum.CODE_500);
                    }
                }

                // 构建完整文件名
                String fileName = article.getTitle() + "_" + file.getOriginalFilename();
                String savePath = fullDirPath + File.separator + fileName;

                // 保存文件
                file.transferTo(new File(savePath));

                // 保存路径给 article
                article.setImagePath(StaticUtil.RESOURCE_URL_IMAGE + relativePath + File.separator + fileName);
            } catch (Exception e) {
                log.error("图片保存失败", e);
            }
        }

        String monthPath = DateUtil.getMonthPath(article.getCreateTime());

        String content = articleVO.getContent();
        if (StringUtils.isNotEmpty(content)) {
            List<String> urlList = MarkdownExtractor.extractImageFileNames(content);
            MarkdownExtractor.moveImagesToTargetDir(urlList, monthPath);
            content = MarkdownExtractor.replaceImagePaths(content, monthPath);
        }

        String filePath = FileWriteUtil.writeMarkdown(content, articleVO.getTitle(), article.getCreateTime(), false);
        article.setStoragePath(filePath);
        if (NumberUtils.isLessThanZero(article.getId())) {
            article.setExist(StaticUtil.TRUE);
            save(article);
            return Results.ok("添加成功");
        }
        updateById(article);
        return Results.ok("修改成功");
    }

    @Override
    public Results<String> updateExist(Integer id, Boolean exist) {
        if (NumberUtils.isLessThanZero(id) || exist == null) {
            log.error("修改文章状态失败,传入的参数存在null,id = {},exist={}", id, exist);
            return Results.fail(ResultCodeEnum.EMPTY_VALUE);
        }

        Article article = getArticleDetail(id, exist);
        if (article == null) {
            log.error("修改失败,当前文章不存在");
            return Results.fail("修改失败,该文章不存在");
        }
        article.setExist(!exist);
        boolean updatedExist = updateById(article);
        if (updatedExist) {
            if (exist)
                return Results.ok("修改成功,当前文章已隐藏");
            else
                return Results.ok("修改成功,当前文章已开启");
        }
        return Results.fail("修改失败,请稍后重试");

    }

    @Override
    public Results<String> articleContent(Integer id) {
        if (NumberUtils.isLessThanZero(id)) {
            log.error("获取文章内容失败,传入的参数为null,id = {}", id);
            return Results.fail(ResultCodeEnum.EMPTY_VALUE);
        }
        Article article = getById(id);
        if (article == null) {
            log.error("获取失败,文章不存在");
            return Results.fail(ResultCodeEnum.CODE_500);
        }
        String storagePath = article.getStoragePath();
        if (StringUtils.isEmpty(storagePath)) {
            log.error("文章存储地址为空,id = {}", id);
            return Results.fail(ResultCodeEnum.CODE_500);
        }
        String markdownFile = FileReaderUtil.readMarkdownFile(storagePath);
        return Results.ok(markdownFile);
    }

    @Override
    public Results<ArticleVO> getArticleData(Integer id) {
        if (NumberUtils.isLessThanZero(id)) {
            log.error("获取文章内容失败,传入的参数为null,id = {}", id);
            return Results.fail(ResultCodeEnum.CODE_500);
        }
        Article article = getById(id);
        if (article == null) {
            log.error("获取失败,文章不存在");
            return Results.fail(ResultCodeEnum.CODE_500);
        }
        String storagePath = article.getStoragePath();
        if (StringUtils.isEmpty(storagePath)) {
            log.error("文章存储地址为空,id = {}", id);
            return Results.fail(ResultCodeEnum.CODE_500);
        }

        String rawMarkdown = FileReaderUtil.readMarkdownFile(storagePath);
        if (StringUtils.isEmpty(rawMarkdown)) {
            rawMarkdown = "";
        }
        String markdownFile = rawMarkdown.replaceFirst("(?s)^---.*?---\\s*", ""); // 关键行
        ArticleVO articleVO = ArticleConvert.INSTANCE.convertVO(article);
        articleVO.setContent(markdownFile);
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
