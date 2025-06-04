package org.deslre.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.deslre.commons.result.ResultCodeEnum;
import org.deslre.commons.result.Results;
import org.deslre.commons.utils.NumberUtils;
import org.deslre.commons.utils.StaticUtil;
import org.deslre.commons.utils.StringUtils;
import org.deslre.user.convert.VisitorConvert;
import org.deslre.user.entity.po.Region;
import org.deslre.user.entity.po.VisitLog;
import org.deslre.user.entity.po.Visitor;
import org.deslre.user.entity.po.VisitorInfo;
import org.deslre.user.entity.vo.VisitorVO;
import org.deslre.user.mapper.VisitorMapper;
import org.deslre.user.service.VisitLogService;
import org.deslre.user.service.VisitorService;
import org.deslre.utils.VisitorUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * ClassName: VisitorServiceImpl
 * Description: 访客服务类
 * Author: Deslrey
 * Date: 2025-06-02 22:40
 * Version: 1.0
 */
@Service
public class VisitorServiceImpl extends ServiceImpl<VisitorMapper, Visitor> implements VisitorService {

    @Resource
    private VisitLogService visitLogService;

    @Override
    public Results<VisitorVO> visitorToken(HttpServletRequest request, String visitorToken, Integer visitorId, String des) {
        if (request == null) {
            return Results.fail(ResultCodeEnum.CODE_500);
        }
        Visitor visitor = null;
        VisitorInfo visitorInfo = null;
        VisitLog visitLog = null;
        Region region = null;
        //      如果为null的话就代表首次访问,信息入库并返回token
        if (StringUtils.isEmpty(visitorToken)) {
            return getVisitorVOResults(request, des);
        } else {
            LambdaQueryWrapper<Visitor> queryWrapper = new LambdaQueryWrapper<Visitor>().eq(Visitor::getId, visitorId).eq(Visitor::getVisitorToken, visitorToken);
            visitor = getOne(queryWrapper);
            if (visitor == null) {
                visitorInfo = VisitorUtil.buildVisitorInfo(request);
                region = visitorInfo.getRegion();
                return getVisitorVOResults(request, des);
            } else {
                visitor.setLastVisit(LocalDateTime.now());
                visitor.setVisitCount(visitor.getVisitCount() + 1);
                updateById(visitor);
                VisitorVO convert = VisitorConvert.INSTANCE.convert(visitor);

                visitorInfo = VisitorUtil.buildVisitorInfo(request);
                region = visitorInfo.getRegion();
                return getVisitorVOResults(visitorInfo, region, convert, des);
            }
        }
    }

    private Results<VisitorVO> getVisitorVOResults(VisitorInfo visitorInfo, Region region, VisitorVO convert, String des) {
        VisitLog visitLog;
        visitLog = new VisitLog();
        visitLog.setVisitorIp(visitorInfo.getIp());
        visitLog.setArticleId(null);
        visitLog.setPlatform(visitorInfo.getPlatform());
        visitLog.setBrowser(visitorInfo.getBrowser());
        visitLog.setDevice(visitorInfo.getDevice());
        visitLog.setProvince(region.getCountry() != null ? region.getCountry() : "未知");
        visitLog.setCity(region.getCity() != null ? region.getCity() : "未知");
        visitLog.setVisitTime(LocalDateTime.now());
        visitLog.setVisitDate(LocalDate.now());
        visitLog.setDescription("IP: " + visitorInfo.getIp() + " 访问: " + des);
        visitLog.setExist(StaticUtil.TRUE);
        visitLogService.save(visitLog);

        return Results.ok(convert);
    }

    private Results<VisitorVO> getVisitorVOResults(HttpServletRequest request, String des) {
        VisitorInfo visitorInfo;
        Region region;
        Visitor visitor;
        VisitLog visitLog;
        visitorInfo = VisitorUtil.buildVisitorInfo(request);
        region = visitorInfo.getRegion();
        visitor = new Visitor();
        visitor.setIp(visitorInfo.getIp());
        visitor.setVisitCount(1);
        visitor.setFirstVisit(LocalDateTime.now());
        visitor.setLastVisit(LocalDateTime.now());
        visitor.setVisitorToken(UUID.randomUUID().toString());
        visitor.setIsBlock(StaticUtil.TRUE);
        save(visitor);
        VisitorVO convert = VisitorConvert.INSTANCE.convert(visitor);

        return getVisitorVOResults(visitorInfo, region, convert, des);
    }


    @Override
    public Results<List<VisitorVO>> visitorList() {
        List<Visitor> visitorList = list();
        if (visitorList == null || visitorList.isEmpty()) {
            return Results.ok(new ArrayList<>(), "暂无访客");
        }
        List<VisitorVO> convertList = VisitorConvert.INSTANCE.convertListVO(visitorList);
        return Results.ok(convertList);
    }

    @Override
    public Results<Boolean> blockVisitor(Integer visitorId, Boolean isBlock) {
        if (NumberUtils.isLessThanZero(visitorId) || isBlock == null) {
            return Results.fail(ResultCodeEnum.DATA_ERROR);
        }
        Visitor visitor = getById(visitorId);
        if (visitor == null) {
            return Results.fail("该用户不存在,请稍后重试!");
        }
        visitor.setIsBlock(!isBlock);
        boolean updated = updateById(visitor);
        if (updated) {
            return Results.ok("修改成功!");
        }
        return Results.fail("修改失败");
    }
}
