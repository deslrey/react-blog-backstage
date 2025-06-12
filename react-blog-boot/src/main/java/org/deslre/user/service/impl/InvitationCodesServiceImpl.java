package org.deslre.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.deslre.commons.result.ResultCodeEnum;
import org.deslre.commons.result.Results;
import org.deslre.commons.utils.NumberUtils;
import org.deslre.commons.utils.StaticUtil;
import org.deslre.commons.utils.StringUtils;
import org.deslre.user.convert.InviteCodeConvert;
import org.deslre.user.entity.dto.UserInfoDTO;
import org.deslre.user.entity.po.InvitationCodes;
import org.deslre.user.entity.po.User;
import org.deslre.user.entity.vo.InviteCodeVO;
import org.deslre.user.mapper.InvitationCodesMapper;
import org.deslre.user.service.InvitationCodesService;
import org.deslre.user.service.UsersService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * ClassName: InvitationCodesServiceImpl
 * Description: 邀请码服务层
 * Author: Deslrey
 * Date: 2025-06-08 20:07
 * Version: 1.0
 */
@Service
public class InvitationCodesServiceImpl extends ServiceImpl<InvitationCodesMapper, InvitationCodes> implements InvitationCodesService {

    @Lazy
    @Resource
    private UsersService usersService;

    @Override
    public Results<Void> addInviteCode(UserInfoDTO userInfoDTO, InviteCodeVO vo) {
        if (userInfoDTO == null) {
            return Results.fail(ResultCodeEnum.CODE_500);
        }
        if (vo == null || StringUtils.isEmpty(vo.getCode())) {
            return Results.fail("邀请码不能为空");
        }
        if (StringUtils.isEmpty(userInfoDTO.getUserName()) || StringUtils.isEmpty(userInfoDTO.getEmail()) || userInfoDTO.getAdmin() == null) {
            return Results.fail(ResultCodeEnum.CODE_501);
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>().eq(User::getEmail, userInfoDTO.getEmail()).eq(User::getUserName, userInfoDTO.getUserName()).eq(User::getAdmin, StaticUtil.TRUE).eq(User::getExist, StaticUtil.TRUE);
        User user = usersService.getOne(queryWrapper);
        if (user == null) {
            return Results.fail(ResultCodeEnum.LOGIN_MOBILE_ERROR);
        }
        LambdaQueryWrapper<InvitationCodes> invitationCodesQueryWrapper = new LambdaQueryWrapper<InvitationCodes>().eq(InvitationCodes::getCode, vo.getCode());
        InvitationCodes invitationCodes = getOne(invitationCodesQueryWrapper);
        if (invitationCodes != null) {
            return Results.fail("该邀请码已存在");
        }
        invitationCodes = new InvitationCodes();
        invitationCodes.setCode(vo.getCode());
        invitationCodes.setIsUsed(StaticUtil.TRUE);
        invitationCodes.setCreatedBy(userInfoDTO.getEmail());
        invitationCodes.setIsAdmin(vo.getIsAdmin());
        invitationCodes.setCreatedTime(LocalDateTime.now());
        invitationCodes.setExpiresTime(vo.getExpiresTime());
        invitationCodes.setExist(StaticUtil.TRUE);
        invitationCodes.setRemark(vo.getRemark());
        save(invitationCodes);
        return Results.ok("邀请码添加成功");
    }

    @Override
    public Results<List<InviteCodeVO>> inviteCodeList() {
        List<InvitationCodes> invitationCodesList = list();
        List<InviteCodeVO> inviteCodeVOList = InviteCodeConvert.INSTANCE.convert(invitationCodesList);
        return Results.ok(inviteCodeVOList);
    }

    @Override
    public Results<Void> updateInviteCode(InviteCodeVO vo) {
        if (vo == null) {
            return Results.fail(ResultCodeEnum.CODE_501);
        }
        if (NumberUtils.isLessThanZero(vo.getId()) || StringUtils.isEmpty(vo.getCode()) || StringUtils.isEmpty(vo.getCreatedBy())) {
            return Results.fail(ResultCodeEnum.CODE_501);
        }
        LambdaQueryWrapper<InvitationCodes> queryWrapper = new LambdaQueryWrapper<InvitationCodes>().eq(InvitationCodes::getId, vo.getId()).eq(InvitationCodes::getCreatedBy, vo.getCreatedBy());
        InvitationCodes invitationCodes = getOne(queryWrapper);
        if (invitationCodes == null) {
            return Results.fail("修改失败,数据不存在");
        }
        invitationCodes.setCode(vo.getCode());
        invitationCodes.setExpiresTime(vo.getExpiresTime());
        invitationCodes.setRemark(vo.getRemark());
        invitationCodes.setIsAdmin(vo.getIsAdmin());
        updateById(invitationCodes);
        return Results.ok("修改成功");
    }
}