package org.deslre.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.deslre.commons.result.ResultCodeEnum;
import org.deslre.commons.result.Results;
import org.deslre.commons.utils.NumberUtils;
import org.deslre.desk.entity.dto.ArticleViewDTO;
import org.deslre.user.convert.VisitLogConvert;
import org.deslre.user.entity.po.VisitCount;
import org.deslre.user.entity.po.VisitLog;
import org.deslre.user.entity.vo.VisitLogVO;
import org.deslre.user.mapper.VisitLogMapper;
import org.deslre.user.service.VisitLogService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public Results<Boolean> updateExist(Integer id, Boolean exist) {
        if (NumberUtils.isLessThanZero(id) || exist == null) {
            return Results.fail(ResultCodeEnum.DATA_ERROR);
        }
        VisitLog visitLog = getById(id);
        if (visitLog == null) {
            return Results.fail(ResultCodeEnum.CODE_500);
        }
        visitLog.setExist(!exist);
        return Results.ok(exist ? "隐藏成功" : "开启成功");
    }

    @Override
    public Results<List<VisitCount>> getDailyTop5Articles() {
        List<VisitCount> dailyTop5Articles = this.baseMapper.getDailyTop5Articles();
        return Results.ok(dailyTop5Articles);
    }

    @Override
    public Results<List<VisitCount>> getLast5DaysVisitCount() {
        List<VisitCount> list = this.baseMapper.selectLast5DaysVisitCount();

        // 补全没有访问记录的日期，保证一定有5天数据
        Map<String, Integer> map = list.stream()
                .collect(Collectors.toMap(VisitCount::getTitle, VisitCount::getCount));

        List<VisitCount> result = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (int i = 4; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            String dateStr = date.toString();
            Integer count = map.getOrDefault(dateStr, 0);
            result.add(new VisitCount(dateStr, count));
        }

        return Results.ok(result);
    }

    @Override
    public Results<List<VisitCount>> getVisitCountByProvince() {
        List<VisitCount> visitCountList = this.getBaseMapper().getVisitCountByProvince();
        return Results.ok(visitCountList);
    }
}