package org.deslre.user.controller;


import org.deslre.commons.result.Results;
import org.deslre.user.entity.vo.ArticleDraftVO;
import org.deslre.user.service.ArticleDraftService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public Results<String> saveArticleDraft(@RequestPart("articleDraft") ArticleDraftVO articleDraftVO, @RequestPart(value = "file", required = false) MultipartFile file) {
        return articleDraftService.saveArticleDraft(articleDraftVO, file);
    }

    @PostMapping("deleteArticleDraft")
    public Results<String> deleteArticleDraft(Integer id) {
        return articleDraftService.deleteArticleDraft(id);
    }
}
