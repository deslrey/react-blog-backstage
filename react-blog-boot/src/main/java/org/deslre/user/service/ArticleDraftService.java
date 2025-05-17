package org.deslre.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.deslre.commons.result.Results;
import org.deslre.user.entity.po.ArticleDraft;
import org.deslre.user.entity.vo.ArticleDraftVO;

import java.util.List;

/**
 * ClassName: ArticleDraft
 * Description: 草稿服务类
 * Author: Deslrey
 * Date: 2025-05-17 19:12
 * Version: 1.0
 */
public interface ArticleDraftService extends IService<ArticleDraft> {

    Results<List<ArticleDraftVO>> getArticleDraftList();

    Results<ArticleDraftVO> getArticleDraftData(Integer id);

    Results<String> saveArticleDraft(ArticleDraftVO articleDraftVO);

    Results<String> deleteArticleDraft(Integer id);
}
