package org.deslre.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.deslre.commons.result.Results;
import org.deslre.user.convert.VisitLogConvert;
import org.deslre.user.entity.po.VisitLog;
import org.deslre.user.entity.vo.VisitLogVO;
import org.deslre.user.mapper.VisitLogMapper;
import org.deslre.user.service.VisitLogService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: VisitLogServiceImpl
 * Description: 访客日志服务类
 * Author: Deslrey
 * Date: 2025-06-02 22:39
 * Version: 1.0
 */
@Service
public class VisitLogServiceImpl extends ServiceImpl<VisitLogMapper, VisitLog> implements VisitLogService {

    @Override
    public Results<List<VisitLogVO>> visitLogList() {
        List<VisitLog> visitLogList = list();
        if (visitLogList == null || visitLogList.isEmpty()) {
            return Results.ok("访客日志暂无数据!");
        }
        List<VisitLogVO> convertList = VisitLogConvert.INSTANCE.convertListVO(visitLogList);
        return Results.ok(convertList);
    }
}