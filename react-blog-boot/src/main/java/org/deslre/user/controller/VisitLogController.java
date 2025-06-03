package org.deslre.user.controller;

import org.deslre.commons.result.Results;
import org.deslre.user.entity.vo.VisitLogVO;
import org.deslre.user.service.VisitLogService;
import org.springframework.web.bind.annotation.GetMapping;
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

}
