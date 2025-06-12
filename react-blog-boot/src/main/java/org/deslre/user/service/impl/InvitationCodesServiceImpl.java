package org.deslre.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.deslre.commons.result.ResultCodeEnum;
import org.deslre.commons.result.Results;
import org.deslre.commons.utils.StaticUtil;
import org.deslre.commons.utils.StringUtils;
import org.deslre.user.entity.dto.UserInfoDTO;
import org.deslre.user.entity.po.InvitationCodes;
import org.deslre.user.entity.po.User;
import org.deslre.user.mapper.InvitationCodesMapper;
import org.deslre.user.service.InvitationCodesService;
import org.deslre.user.service.UsersService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

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
    public Results<Void> addInviteCode(UserInfoDTO userInfoDTO, String inviteCode, String remark, Boolean isAdmin) {
        if (userInfoDTO == null) {
            return Results.fail(ResultCodeEnum.CODE_500);
        }
        if (StringUtils.isEmpty(inviteCode)) {
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
        LambdaQueryWrapper<InvitationCodes> invitationCodesQueryWrapper = new LambdaQueryWrapper<InvitationCodes>().eq(InvitationCodes::getCode, inviteCode);
        InvitationCodes invitationCodes = getOne(invitationCodesQueryWrapper);
        if (invitationCodes != null) {
            return Results.fail("该邀请码已存在");
        }
        invitationCodes = new InvitationCodes();
        invitationCodes.setCode(inviteCode);
        invitationCodes.setIsUsed(StaticUtil.TRUE);
        invitationCodes.setCreatedBy(user.getId());
        invitationCodes.setIsAdmin(isAdmin);
        invitationCodes.setCreatedTime(LocalDateTime.now());
        invitationCodes.setExist(StaticUtil.TRUE);
        invitationCodes.setRemark(remark);
        save(invitationCodes);
        return Results.ok("邀请码添加成功");
    }
}