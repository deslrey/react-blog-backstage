package org.deslre.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.deslre.commons.result.ResultCodeEnum;
import org.deslre.commons.result.Results;
import org.deslre.commons.utils.StaticUtil;
import org.deslre.commons.utils.StringUtils;
import org.deslre.user.entity.po.InvitationCodes;
import org.deslre.user.entity.po.Users;
import org.deslre.user.mapper.UsersMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.deslre.user.service.InvitationCodesService;
import org.deslre.user.service.UsersService;
import org.deslre.utils.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author author
 * @since 2025-06-08
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

    @Resource
    private InvitationCodesService invitationCodesService;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public Results<Void> register(String email, String inviteCode) {
        if (StringUtils.isEmpty(email)) {
            return Results.fail("邮箱号不能为空");
        }

        if (!RegexUtils.checkEmail(email)) {
            return Results.fail("邮箱号格式错误");
        }

        if (StringUtils.isEmpty(inviteCode)) {
            return Results.fail("邀请码为空");
        }

        LambdaQueryWrapper<InvitationCodes> queryWrapper = new LambdaQueryWrapper<InvitationCodes>()
                .eq(InvitationCodes::getCode, inviteCode)
                .eq(InvitationCodes::getExist, StaticUtil.TRUE);

        InvitationCodes invitationCode = invitationCodesService.getOne(queryWrapper);

        if (invitationCode == null) {
            return Results.fail("无效邀请码");
        }

        if (invitationCode.getIsUsed()) {
            return Results.fail("邀请码已被使用");
        }

        if (invitationCode.getExpiresTime() != null &&
                invitationCode.getExpiresTime().isBefore(LocalDateTime.now())) {
            return Results.fail("邀请码已过期");
        }

        String code = redisUtil.get(Constants.EMAIL_CODE);
        if (StringUtils.isNotEmpty(code)) {
            return Results.fail("请勿重复申请验证码");
        }

        String generatedCode = VerificationCodeGenerator.generateCode();
        EmailSender.sendEmail(email, generatedCode);
        redisUtil.setEx(Constants.EMAIL_CODE, generatedCode, Constants.LENGTH_10, TimeUnit.MINUTES);
        return Results.ok("验证码已发送,请检查您的邮箱");
    }

}
