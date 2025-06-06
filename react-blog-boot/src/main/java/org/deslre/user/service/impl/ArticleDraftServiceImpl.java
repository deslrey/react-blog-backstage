package org.deslre.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.deslre.commons.result.ResultCodeEnum;
import org.deslre.commons.result.Results;
import org.deslre.commons.utils.NumberUtils;
import org.deslre.commons.utils.StaticUtil;
import org.deslre.user.convert.ArticleDraftConvert;
import org.deslre.user.entity.po.ArticleDraft;
import org.deslre.user.entity.vo.ArticleDraftVO;
import org.deslre.user.mapper.ArticleDraftMapper;
import org.deslre.user.service.ArticleDraftService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * ClassName: ArticleDraft
 * Description: 草稿服务类实现
 * Author: Deslrey
 * Date: 2025-05-17 19:12
 * Version: 1.0
 */
@Slf4j
@Service
public class ArticleDraftServiceImpl extends ServiceImpl<ArticleDraftMapper, ArticleDraft> implements ArticleDraftService {
    @Override
    public Results<List<ArticleDraftVO>> getArticleDraftList() {
        LambdaQueryWrapper<ArticleDraft> queryWrapper = new LambdaQueryWrapper<ArticleDraft>().eq(ArticleDraft::getExist, StaticUtil.TRUE);
        List<ArticleDraft> articleDraftList = list(queryWrapper);
        if (articleDraftList == null) {
            return Results.ok(new ArrayList<>());
        }
        List<ArticleDraftVO> convertList = ArticleDraftConvert.INSTANCE.convertList(articleDraftList);
        return Results.ok(convertList);
    }

    @Override
    public Results<ArticleDraftVO> getArticleDraftData(Integer id) {
        if (NumberUtils.isLessThanZero(id)) {
            log.error("获取草稿数据传入的id异常 ======{}", id);
            return Results.fail(ResultCodeEnum.DATA_ERROR);
        }
        LambdaQueryWrapper<ArticleDraft> queryWrapper = new LambdaQueryWrapper<ArticleDraft>().eq(ArticleDraft::getId, id).eq(ArticleDraft::getExist, StaticUtil.TRUE);
        ArticleDraft articleDraft = getOne(queryWrapper);
        if (articleDraft == null) {
            log.error("查询的草稿不存在 id ======> {}", id);
            return Results.fail("该草稿不存在");
        }
        ArticleDraftVO articleDraftVO = ArticleDraftConvert.INSTANCE.convert(articleDraft);
        return Results.ok(articleDraftVO);
    }

    @Override
    public Results<String> saveArticleDraft(ArticleDraftVO articleDraftVO, MultipartFile file) {
        System.out.println("articleDraftVO = " + articleDraftVO);
        if (articleDraftVO == null) {
            log.error("保存的草稿传入值为 null");
            return Results.fail(ResultCodeEnum.DATA_ERROR);
        }
        articleDraftVO.setUpdateTime(LocalDateTime.now());
        ArticleDraft articleDraft = ArticleDraftConvert.INSTANCE.convert(articleDraftVO);
        if (file != null) {
            try {
                File dir = new File(StaticUtil.RESOURCE_DRAFT_PATH);
                if (!dir.exists()) {
                    boolean mkdir = dir.mkdirs();
                    if (!mkdir) {
                        log.error("目录创建失败: " + StaticUtil.RESOURCE_DRAFT_PATH);
                        return Results.fail(ResultCodeEnum.CODE_500);
                    }
                }
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                String savePath = StaticUtil.RESOURCE_DRAFT_PATH + File.separator + fileName;
                file.transferTo(new File(savePath));
                articleDraft.setImagePath(StaticUtil.RESOURCE_DRAFT + fileName);
            } catch (Exception e) {
                log.error("保存草稿封面出现异常: {}", e.getMessage());
                return Results.fail(ResultCodeEnum.CODE_500);
            }
        }

        if (articleDraft.getArticleId() != null) {
            LambdaQueryWrapper<ArticleDraft> queryWrapper = new LambdaQueryWrapper<ArticleDraft>().eq(ArticleDraft::getArticleId, articleDraft.getArticleId()).eq(ArticleDraft::getExist, StaticUtil.TRUE);
            ArticleDraft draft = getOne(queryWrapper);
            if (draft == null) {
                articleDraft.setId(null);
                articleDraft.setExist(StaticUtil.TRUE);
                save(articleDraft);
                return Results.ok("草稿保存成功");
            }
            articleDraft.setId(draft.getId());
            updateById(articleDraft);
            return Results.ok("草稿保存成功");
        }

        if (articleDraft.getId() == null) {
            articleDraft.setExist(StaticUtil.TRUE);
            save(articleDraft);
        } else {
            ArticleDraft draft = getById(articleDraft.getId());
            if (draft != null)
                updateById(articleDraft);
        }
        return Results.ok("草稿保存成功");
    }

    @Override
    public Results<String> deleteArticleDraft(Integer articleId, Integer articleDraftId) {
        if (NumberUtils.isLessThanZero(articleDraftId)) {
            log.error("删除的草稿id为 ======> {}", articleDraftId);
            return Results.fail("该草稿不存在");
        }
        LambdaQueryWrapper<ArticleDraft> queryWrapper = new LambdaQueryWrapper<ArticleDraft>().eq(ArticleDraft::getId, articleDraftId).eq(ArticleDraft::getExist, StaticUtil.TRUE);
        ArticleDraft articleDraft = getOne(queryWrapper);
        if (articleDraft == null) {
            log.info("保存删除草稿,该草稿不存在,id ======{}", articleDraftId);
            return Results.ok();
        }
        articleDraft.setExist(StaticUtil.FALSE);
        updateById(articleDraft);
        return Results.ok();
    }

    @Override
    public Results<String> deleteDraft(Integer id) {
        if (NumberUtils.isLessThanZero(id)) {
            log.error("删除的草稿id为 ======> {}", id);
            return Results.fail("该草稿不存在");
        }

        LambdaQueryWrapper<ArticleDraft> queryWrapper = new LambdaQueryWrapper<ArticleDraft>().eq(ArticleDraft::getId, id).eq(ArticleDraft::getExist, StaticUtil.TRUE);
        ArticleDraft articleDraft = getOne(queryWrapper);
        if (articleDraft == null) {
            return Results.fail("该草稿不存在");
        }
        articleDraft.setExist(StaticUtil.FALSE);
        updateById(articleDraft);
        return Results.ok("删除成功");
    }
}
