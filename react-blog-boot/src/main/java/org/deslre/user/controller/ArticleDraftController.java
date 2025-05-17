package org.deslre.user.controller;


import org.deslre.commons.result.Results;
import org.deslre.user.entity.vo.ArticleDraftVO;
import org.deslre.user.service.ArticleDraftService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * ClassName: ArticleDraftController
 * Description: 草稿操作控制器
 * Author: Deslrey
 * Date: 2025-05-17 19:12
 * Version: 1.0
 */
@RestController
@RequestMapping("/articleDraft")
public class ArticleDraftController {

    @Resource
    private ArticleDraftService articleDraftService;

    @GetMapping("getArticleDraftList")
    public Results<List<ArticleDraftVO>> getArticleDraftList() {
        return articleDraftService.getArticleDraftList();
    }

    @PostMapping("getArticleDraftData")
    public Results<ArticleDraftVO> getArticleDraftData(Integer id) {
        return articleDraftService.getArticleDraftData(id);
    }

    @PostMapping("saveArticleDraft")
    public Results<String> saveArticleDraft(ArticleDraftVO articleDraftVO) {
        return articleDraftService.saveArticleDraft(articleDraftVO);
    }

    @PostMapping("deleteArticleDraft")
    public Results<String> deleteArticleDraft(Integer id) {
        return articleDraftService.deleteArticleDraft(id);
    }
}
