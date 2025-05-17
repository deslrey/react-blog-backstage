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

import java.util.ArrayList;
import java.util.List;

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
    public Results<String> saveArticleDraft(ArticleDraftVO articleDraftVO) {
        if (articleDraftVO == null) {
            log.error("保存的草稿传入值为 null");
            return Results.fail(ResultCodeEnum.DATA_ERROR);
        }
        if (NumberUtils.isLessThanZero(articleDraftVO.getId())) {
            log.error("获取草稿数据传入的id异常 ======{}", articleDraftVO.getId());
            return Results.fail(ResultCodeEnum.DATA_ERROR);
        }
        ArticleDraft articleDraft = ArticleDraftConvert.INSTANCE.convert(articleDraftVO);
        ArticleDraft draft = getById(articleDraft.getId());
        if (draft == null) {
            articleDraft.setExist(StaticUtil.TRUE);
            save(articleDraft);
            return Results.ok("保存成功");
        }
        updateById(articleDraft);
        return Results.ok("保存成功");
    }

    @Override
    public Results<String> deleteArticleDraft(Integer id) {
        if (NumberUtils.isLessThanZero(id)) {
            log.error("删除的草稿id为 ======> {}", id);
            return Results.fail("该草稿不存在");
        }
        ArticleDraft articleDraft = getById(id);
        if (articleDraft == null) {
            return Results.ok();
        }
        articleDraft.setExist(false);
        updateById(articleDraft);
        return Results.ok();
    }
}
