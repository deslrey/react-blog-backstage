package org.deslre.user.controller;

import org.deslre.commons.result.Results;
import org.deslre.desk.entity.dto.ArticleViewDTO;
import org.deslre.user.entity.po.DailyVisitCount;
import org.deslre.user.entity.vo.VisitLogVO;
import org.deslre.user.service.VisitLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * ClassName: VisitLogController
 * Description: 访客日志控制层
 * Author: Deslrey
 * Date: 2025-06-02 22:35
 * Version: 1.0
 */
@RestController
@RequestMapping("/visit-log")
public class VisitLogController {

    @Resource
    private VisitLogService visitLogService;

    @GetMapping("visitLogList")
    public Results<List<VisitLogVO>> visitLogList() {
        return visitLogService.visitLogList();
    }

    @PostMapping("updateExist")
    public Results<Boolean> updateExist(Integer id, Boolean exist) {
        return visitLogService.updateExist(id, exist);
    }

    @GetMapping("/top5")
    public Results<List<ArticleViewDTO>> getDailyTop5Articles() {
        return visitLogService.getDailyTop5Articles();
    }

    @GetMapping("/last5days")
    public Results<List<DailyVisitCount>> getLast5DaysVisitCount() {
        return visitLogService.getLast5DaysVisitCount();
    }
}
