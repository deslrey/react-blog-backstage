package org.deslre.user.controller;

import org.deslre.commons.result.Results;
import org.deslre.user.entity.vo.VisitorVO;
import org.deslre.user.service.VisitorService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ClassName: VisitorController
 * Description: 访问用户控制层
 * Author: Deslrey
 * Date: 2025-06-02 22:35
 * Version: 1.0
 */
@RestController
@RequestMapping("/visitor")
public class VisitorController {

    @Resource
    private VisitorService visitorService;

    @PostMapping("visitorToken")
    public Results<VisitorVO> visitorToken(HttpServletRequest request,
                                           @RequestHeader(value = "X-Visitor-Token", required = false) String visitorToken,
                                           @RequestHeader(value = "X-Visitor-Id", required = false) Integer visitorId,
                                           String des) {
        return visitorService.visitorToken(request, visitorToken, visitorId, des);
    }

    @GetMapping("visitorList")
    public Results<List<VisitorVO>> visitorList() {
        return visitorService.visitorList();
    }

    @PostMapping("blockVisitor")
    public Results<Boolean> blockVisitor(Integer visitorId, Boolean isBlock) {
        return visitorService.blockVisitor(visitorId, isBlock);
    }
}

