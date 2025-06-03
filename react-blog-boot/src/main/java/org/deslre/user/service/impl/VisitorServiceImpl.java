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
import org.deslre.user.entity.po.Visitor;
import org.deslre.user.entity.vo.VisitorVO;
import org.deslre.user.mapper.VisitorMapper;
import org.deslre.user.service.VisitorService;
import org.deslre.utils.IpAddressUtil;
import org.deslre.utils.VisitorUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: VisitorServiceImpl
 * Description: 访客服务类
 * Author: Deslrey
 * Date: 2025-06-02 22:40
 * Version: 1.0
 */
@Service
public class VisitorServiceImpl extends ServiceImpl<VisitorMapper, Visitor> implements VisitorService {

    @Override
    public Results<VisitorVO> visitorToken(HttpServletRequest request, String visitorToken, Integer visitorId) {
        if (request == null) {
            return Results.fail(ResultCodeEnum.CODE_500);
        }
        Visitor visitor = null;
        //      如果为null的话就代表首次访问,信息入库并返回token
        if (StringUtils.isEmpty(visitorToken)) {
            visitor = VisitorUtil.buildVisitorInfo(request);
            Region region = IpAddressUtil.getIpAddressByOnline(visitor.getIp());
            System.out.println("region = " + region);
            if (region != null) {
                visitor.setProvince(region.getCountry());
                visitor.setCity(region.getCity());
            } else {
                visitor.setProvince("未知");
                visitor.setCity("未知");
            }
            save(visitor);
            VisitorVO convert = VisitorConvert.INSTANCE.convert(visitor);
            return Results.ok(convert);
        } else {
            LambdaQueryWrapper<Visitor> queryWrapper = new LambdaQueryWrapper<Visitor>().eq(Visitor::getVisitorToken, visitorToken);
            visitor = getOne(queryWrapper);
            if (visitor == null) {
                visitor = VisitorUtil.buildVisitorInfo(request);
                Region region = IpAddressUtil.getIpAddressByOnline(visitor.getIp());
                System.out.println("region = " + region);
                if (region != null) {
                    visitor.setProvince(region.getCountry());
                    visitor.setCity(region.getCity());
                } else {
                    visitor.setProvince("未知");
                    visitor.setCity("未知");
                }
                save(visitor);
                VisitorVO convert = VisitorConvert.INSTANCE.convert(visitor);
                return Results.ok(convert);
            } else {
                visitor.setLastVisit(LocalDateTime.now());
                visitor.setVisitCount(visitor.getVisitCount() + 1);
                updateById(visitor);
                VisitorVO convert = VisitorConvert.INSTANCE.convert(visitor);
                return Results.ok(convert);
            }
        }
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
