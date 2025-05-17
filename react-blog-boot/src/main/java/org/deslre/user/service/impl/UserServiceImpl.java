package org.deslre.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.deslre.commons.result.ResultCodeEnum;
import org.deslre.commons.result.Results;
import org.deslre.commons.utils.StringUtils;
import org.deslre.user.entity.po.User;
import org.deslre.user.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.deslre.user.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author deslre
 * @since 2025-04-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Results<User> login(String username, String password) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return Results.fail(ResultCodeEnum.CODE_501);
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>().eq(User::getUsername, username).eq(User::getPassword, password);
        User user = getOne(queryWrapper);
        if (user == null) {
            return Results.fail(ResultCodeEnum.ACCOUNT_ERROR);
        }
        return Results.ok(user);
    }
}
