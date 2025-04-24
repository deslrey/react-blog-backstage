package org.deslre.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.deslre.commons.result.Results;
import org.deslre.commons.utils.NumberUtils;
import org.deslre.exception.DeslreException;
import org.deslre.user.entity.Permission;
import org.deslre.user.entity.User;
import org.deslre.user.entity.UserPermission;
import org.deslre.user.mapper.PermissionMapper;
import org.deslre.user.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.deslre.user.service.PermissionService;
import org.deslre.user.service.UserPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author author
 * @since 2025-04-24
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private UserPermissionService userPermissionService;

    @Override
    public Results<List<Permission>> getFinalPermissionsByUserId(Integer userId) {

        if (NumberUtils.isLessThanZero(userId)) {
            throw new DeslreException("参数异常");
        }

        // 1. 获取角色权限
        List<Permission> rolePermissions = getRolePermissionsByUserId(userId);

        // 2. 获取用户自定义权限
        List<Permission> userPermissions = getUserPermissionsByUserId(userId);

        // 3. 合并角色权限和用户权限
        Set<Permission> finalPermissions = new HashSet<>(rolePermissions);
        finalPermissions.addAll(userPermissions);

        // 4. 去除禁用权限
        List<Permission> collect = finalPermissions.stream()
                .filter(permission -> !isPermissionDisabled(userId, permission.getId()))
                .toList();

        return Results.ok(collect);
    }

    // 获取角色权限
    private List<Permission> getRolePermissionsByUserId(Integer userId) {
        return permissionMapper.selectBySql(userId);
    }

    // 获取用户自定义权限
    private List<Permission> getUserPermissionsByUserId(Integer userId) {
        LambdaQueryWrapper<UserPermission> queryWrapper = new LambdaQueryWrapper<UserPermission>().eq(UserPermission::getUserId, userId).eq(UserPermission::getIsGranted, true);
        return userPermissionService.list(queryWrapper)
                .stream()
                .map(userPermission -> permissionMapper.selectById(userPermission.getPermissionId()))
                .collect(Collectors.toList());
    }

    // 检查权限是否被禁用
    private boolean isPermissionDisabled(Integer userId, Integer permissionId) {
        UserPermission userPermission = userPermissionService.getOne(new QueryWrapper<UserPermission>()
                .eq("user_id", userId)
                .eq("permission_id", permissionId)
                .eq("is_granted", false));

        return userPermission != null;
    }
}
