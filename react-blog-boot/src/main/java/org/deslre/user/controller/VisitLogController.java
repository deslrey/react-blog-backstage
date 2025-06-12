package org.deslre.user.controller;

import org.deslre.annotation.AuthCheck;
import org.deslre.result.Results;
import org.deslre.user.entity.po.VisitCount;
import org.deslre.user.entity.vo.VisitLogVO;
import org.deslre.user.service.VisitLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    @AuthCheck(admin = true, checkLogin = true, log = "获取访客日志列表", category = "visit-log")
    public Results<List<VisitLogVO>> visitLogList(HttpServletRequest request) {
        return visitLogService.visitLogList();
    }

    @PostMapping("updateExist")
    @AuthCheck(admin = true, checkLogin = true, log = "禁用/启用单条访客日志", category = "visit-log")
    public Results<Boolean> updateExist(HttpServletRequest request, Integer id, Boolean exist) {
        return visitLogService.updateExist(id, exist);
    }

    @GetMapping("/top5")
    @AuthCheck(admin = false, checkLogin = true, log = "获取访问量最高的前五篇文章", category = "visit-log")
    public Results<List<VisitCount>> getDailyTop5Articles(HttpServletRequest request) {
        return visitLogService.getDailyTop5Articles();
    }

    @GetMapping("/last5days")
    @AuthCheck(admin = false, checkLogin = true, log = "获取最近一天内访问量最高的前五篇文章", category = "visit-log")
    public Results<List<VisitCount>> getLast5DaysVisitCount(HttpServletRequest request) {
        return visitLogService.getLast5DaysVisitCount();
    }

    @GetMapping("getVisitCountByProvince")
    @AuthCheck(admin = false, checkLogin = true, log = "获取访客地区列表", category = "visit-log")
    public Results<List<VisitCount>> getVisitCountByProvince(HttpServletRequest request) {
        return visitLogService.getVisitCountByProvince();
    }
}
