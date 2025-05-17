package org.deslre.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.deslre.commons.result.Results;
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
}
