package org.deslre.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.deslre.commons.result.ResultCodeEnum;
import org.deslre.commons.result.Results;
import org.deslre.commons.utils.NumberUtils;
import org.deslre.commons.utils.StaticUtil;
import org.deslre.commons.utils.StringUtils;
import org.deslre.user.convert.UserConvert;
import org.deslre.user.entity.po.InvitationCodes;
import org.deslre.user.entity.po.User;
import org.deslre.user.entity.vo.UserVO;
import org.deslre.user.mapper.UsersMapper;
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
@Slf4j
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, User> implements UsersService {

    @Resource
    private InvitationCodesService invitationCodesService;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public Results<Void> register(String userName, String email, String passWord, String inviteCode) {
        if (StringUtils.isEmpty(email)) {
            return Results.fail("邮箱号不能为空");
        }

        if (!RegexUtils.checkEmail(email)) {
            return Results.fail("邮箱号格式错误");
        }

        if (StringUtils.isEmpty(userName)) {
            return Results.fail("用户名不能为空");
        }

        if (StringUtils.isEmpty(passWord)) {
            return Results.fail("密码不能为空");
        }

        if (StringUtils.isEmpty(inviteCode)) {
            return Results.fail("邀请码为空");
        }

//        LambdaQueryWrapper<InvitationCodes> queryWrapper = new LambdaQueryWrapper<InvitationCodes>()
//                .eq(InvitationCodes::getCode, inviteCode)
//                .eq(InvitationCodes::getExist, StaticUtil.TRUE);
//
//        InvitationCodes invitationCode = invitationCodesService.getOne(queryWrapper);
//
//        if (invitationCode == null) {
//            return Results.fail("无效邀请码");
//        }
//
//        if (invitationCode.getIsUsed()) {
//            return Results.fail("邀请码已被使用");
//        }
//
//        if (invitationCode.getExpiresTime() != null &&
//                invitationCode.getExpiresTime().isBefore(LocalDateTime.now())) {
//            return Results.fail("邀请码已过期");
//        }

        String code = redisUtil.get(Constants.EMAIL_CODE + email);
        if (StringUtils.isEmpty(code)) {
            return Results.fail("邮箱验证码过期");
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>().eq(User::getEmail, email);
        User user = getOne(queryWrapper);
        if (user != null) {
            return Results.fail("该邮箱已注册");
        }
        String encrypt = SHA256Util.encrypt(passWord);
        user = new User();
        user.setUserName(userName);
        user.setPassWord(encrypt);
        user.setEmail(email);
        user.setInviteCode(inviteCode);
        user.setImage(StaticUtil.DEFAULT_AVATAR);
        user.setAdmin(StaticUtil.TRUE);
        user.setCreatedTime(LocalDateTime.now());
        user.setLastTime(LocalDateTime.now());
        user.setExist(StaticUtil.TRUE);
        save(user);
        return Results.ok("注册成功");
    }

    @Override
    public Results<UserVO> login(String email, String passWord) {
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(passWord)) {
            return Results.fail("不能传入空值");
        }
        if (!RegexUtils.checkEmail(email)) {
            return Results.fail("邮箱号格式错误");
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>().eq(User::getEmail, email);
        User user = getOne(queryWrapper);
        if (user == null) {
            return Results.fail("登录失败,该邮箱不存在");
        }
        String encrypt = SHA256Util.encrypt(passWord);
        if (!user.getPassWord().equals(encrypt)) {
            return Results.fail("密码错误");
        }
        if (!user.getExist()) {
            return Results.fail("该邮箱已被锁定");
        }
        UserVO vo = UserConvert.INSTANCE.convert(user);
        return Results.ok(vo);
    }

    @Override
    public Results<Void> block(Integer userId, Boolean exist) {
        if (NumberUtils.isLessThanZero(userId) || exist == null) {
            return Results.fail(ResultCodeEnum.EMPTY_VALUE);
        }
        return null;
    }

    @Override
    public Results<Void> sendEmailCode(String email) {
        if (StringUtils.isEmpty(email)) {
            return Results.fail("邮箱号不能为空");
        }

        if (!RegexUtils.checkEmail(email)) {
            return Results.fail("邮箱号格式错误");
        }

        String emailCode = redisUtil.get(Constants.EMAIL_CODE + email);
        if (StringUtils.isNotEmpty(emailCode)) {
            return Results.fail("请勿重复申请邮箱验证码");
        }

        String code = VerificationCodeGenerator.generateCode();
        EmailSender.sendEmail(email, code);
        redisUtil.setEx(Constants.EMAIL_CODE + email, code, Constants.LENGTH_5, TimeUnit.MINUTES);
        log.info("发送验证码邮箱: {},验证码: {}", email, code);
        return Results.ok("验证码已发送,五分钟有效期");
    }
}
