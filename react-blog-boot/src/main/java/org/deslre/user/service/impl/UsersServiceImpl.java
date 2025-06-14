package org.deslre.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.deslre.result.ResultCodeEnum;
import org.deslre.result.Results;
import org.deslre.user.entity.po.InvitationCodes;
import org.deslre.utils.NumberUtils;
import org.deslre.utils.StaticUtil;
import org.deslre.utils.StringUtils;
import org.deslre.user.convert.UserConvert;
import org.deslre.user.entity.po.User;
import org.deslre.user.entity.vo.UserVO;
import org.deslre.user.mapper.UsersMapper;
import org.deslre.user.service.InvitationCodesService;
import org.deslre.user.service.UsersService;
import org.deslre.utils.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
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

    @Lazy
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

        LambdaQueryWrapper<InvitationCodes> inviteCodeQueryWrapper = new LambdaQueryWrapper<InvitationCodes>()
                .eq(InvitationCodes::getCode, inviteCode)
                .eq(InvitationCodes::getExist, StaticUtil.TRUE);

        InvitationCodes invitationCode = invitationCodesService.getOne(inviteCodeQueryWrapper);

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

        String code = redisUtil.get(Constants.EMAIL_CODE + email);
        if (StringUtils.isEmpty(code)) {
            return Results.fail("邮箱验证码过期");
        }
        LambdaQueryWrapper<User> UserQueryWrapper = new LambdaQueryWrapper<User>().eq(User::getEmail, email);
        User user = getOne(UserQueryWrapper);
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
        redisUtil.setEx(Constants.LOGIN_EMAIL + email, "login", Constants.LENGTH_30, TimeUnit.MINUTES);
        return Results.ok(vo, "登录成功");
    }

    @Override
    public Results<Void> block(Integer userId, String email, Boolean exist) {
        if (NumberUtils.isLessThanZero(userId) || StringUtils.isEmpty(email) || exist == null) {
            return Results.fail(ResultCodeEnum.EMPTY_VALUE);
        }
        if (!RegexUtils.checkEmail(email)) {
            return Results.fail(ResultCodeEnum.DATA_ERROR);
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>().eq(User::getId, userId).eq(User::getEmail, email).eq(User::getExist, exist);
        User user = getOne(queryWrapper);
        if (user == null) {
            return Results.fail(exist ? "禁用失败,该用户不存在" : "启用失败,该用户不存在");
        }
        user.setExist(!exist);
        updateById(user);
        return Results.ok(exist ? "禁用成功" : "启用成功");
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

    @Override
    public Results<UserVO> updatePassWord(String email, String oldPassWord, String newPassWord) {
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(oldPassWord) || StringUtils.isEmpty(newPassWord)) {
            return Results.fail(ResultCodeEnum.EMPTY_VALUE);
        }
        if (!RegexUtils.checkEmail(email)) {
            return Results.fail("邮箱号格式错误");
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>().eq(User::getEmail, email);
        User user = getOne(queryWrapper);
        if (user == null) {
            return Results.fail("修改失败,该邮箱不存在");
        }
        String oldEncrypt = SHA256Util.encrypt(oldPassWord);
        String newEncrypt = SHA256Util.encrypt(newPassWord);
        if (Objects.equals(oldEncrypt, newEncrypt)) {
            return Results.fail("修改失败,新密码和旧密码不能相同");
        }
        if (!user.getPassWord().equals(oldEncrypt)) {
            return Results.fail("修改失败,原密码错误");
        }
        user.setPassWord(newEncrypt);
        updateById(user);
        return Results.ok("密码修改成功");
    }

    @Override
    public Results<UserVO> updateUserName(String email, String userName) {
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(userName)) {
            return Results.fail(ResultCodeEnum.EMPTY_VALUE);
        }
        if (!RegexUtils.checkEmail(email)) {
            return Results.fail("邮箱号格式错误");
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>().eq(User::getEmail, email);
        User user = getOne(queryWrapper);
        if (user == null) {
            return Results.fail("修改失败,该用户不存在");
        }
        if (user.getUserName().equals(userName)) {
            return Results.fail("修改失败,新昵称和旧昵称不能相同");
        }
        user.setUserName(userName);
        updateById(user);
        UserVO vo = UserConvert.INSTANCE.convert(user);
        return Results.ok(vo, "昵称修改成功");
    }

    @Override
    public Results<UserVO> updateAvatar(String email, MultipartFile avatarFile) {
        if (StringUtils.isEmpty(email) || avatarFile == null) {
            return Results.fail(ResultCodeEnum.EMPTY_VALUE);
        }
        if (!RegexUtils.checkEmail(email)) {
            return Results.fail("邮箱号格式错误");
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>().eq(User::getEmail, email).eq(User::getExist, StaticUtil.TRUE);
        User user = getOne(queryWrapper);
        if (user == null) {
            return Results.fail("上传失败,该用户不存在");
        }
        try {
            String avatarName = UUID.randomUUID() + "_" + avatarFile.getOriginalFilename();
            File dir = new File(StaticUtil.RESOURCE_AVATAR);
            if (!dir.exists()) {
                boolean created = dir.mkdirs();
                if (!created) {
                    log.error("目录创建失败: {}", StaticUtil.RESOURCE_AVATAR);
                    return Results.fail(ResultCodeEnum.CODE_500);
                }
            }
            String fullAvatarPath = StaticUtil.RESOURCE_AVATAR + File.separator + avatarName;
            avatarFile.transferTo(new File(fullAvatarPath));
            String avatarUrl = StaticUtil.RESOURCE_AVATAR_URL + avatarName;
            System.out.println("avatarUrl = " + avatarUrl);
            user.setImage(avatarUrl);
            updateById(user);
            UserVO vo = UserConvert.INSTANCE.convert(user);
            return Results.ok(vo, "头像上传成功");
        } catch (Exception e) {
            log.error("用户: {},修改头像失败: {}", email, e.getMessage());
            return Results.fail("头像上传失败");
        }
    }

    @Override
    public Results<UserVO> checkUserInfo(String email, Boolean admin) {
        if (StringUtils.isEmpty(email) || admin == null) {
            return Results.fail(ResultCodeEnum.CODE_500);
        }
        if (!RegexUtils.checkEmail(email)) {
            return Results.fail("邮箱号格式错误");
        }

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>().eq(User::getEmail, email).eq(User::getAdmin, admin);
        User user = getOne(queryWrapper);
        if (user == null) {
            return Results.fail("身份验证失败");
        }
        if (!user.getExist()) {
            return Results.fail("请求失败,该用户已被拉黑");
        }
        UserVO vo = UserConvert.INSTANCE.convert(user);
        return Results.ok(vo);
    }

    @Override
    public Results<List<User>> userList() {
        List<User> list = list();
        return Results.ok(list);
    }
}
