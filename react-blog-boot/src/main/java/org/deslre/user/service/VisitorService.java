package org.deslre.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.deslre.commons.result.Results;
import org.deslre.user.entity.po.Visitor;
import org.deslre.user.entity.vo.VisitorVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ClassName: VisitorService
 * Description: 访客服务类
 * Author: Deslrey
 * Date: 2025-06-02 22:39
 * Version: 1.0
 */
public interface VisitorService extends IService<Visitor> {
    Results<VisitorVO> visitorToken(HttpServletRequest request, String visitorToken, Integer visitorId);

    Results<List<VisitorVO>> visitorList();

    Results<Boolean> blockVisitor(Integer visitorId, Boolean isBlock);
}
