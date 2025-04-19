package org.deslre.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.deslre.commons.result.Results;
import org.deslre.commons.utils.EncryptUtil;
import org.deslre.commons.utils.StaticUtil;
import org.deslre.commons.utils.StringUtils;
import org.deslre.exception.DeslreException;
import org.deslre.user.convert.UserConvert;
import org.deslre.user.entity.po.User;
import org.deslre.user.entity.vo.UserVO;
import org.deslre.user.mapper.UserMapper;
import org.deslre.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author author
 * @since 2025-04-18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Override
    public Results<UserVO> register(String userName, String passWord) {
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(passWord)) {
            throw new DeslreException("登录失败,用户名或密码为空");
        }

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>().eq(User::getUserName, userName);
        User user = getOne(queryWrapper);
        if (user != null) {
            throw new DeslreException("注册失败,该用户名已被注册");
        }

        String encrypt = EncryptUtil.encrypt(passWord);
        user = new User();
        user.setUserName(userName);
        user.setPassWord(passWord);
        user.setExist(StaticUtil.TRUE);
        boolean save = save(user);
        if (save) {
            return Results.ok("注册成功");
        }
        return Results.fail("注册失败");
    }

    @Override
    public Results<UserVO> userLogin(String userName, String passWord) {
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(passWord)) {
            throw new DeslreException("登录失败,用户名或密码为空");
        }

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>().eq(User::getUserName, userName).eq(User::getPassWord, passWord);
        User user = getOne(queryWrapper);
        if (user == null) {
            throw new DeslreException("登录失败,该用户不存在");
        }

        if (!user.getExist()) {
            throw new DeslreException("登录失败,该用户已被锁定");
        }

        UserVO userVO = UserConvert.INSTANCE.convert(user);
        return Results.ok(userVO);
    }
}
