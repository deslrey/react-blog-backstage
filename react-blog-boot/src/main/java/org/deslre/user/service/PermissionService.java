package org.deslre.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.deslre.commons.result.Results;
import org.deslre.user.entity.Permission;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2025-04-24
 */
public interface PermissionService extends IService<Permission> {
    Results<List<Permission>> getFinalPermissionsByUserId(Integer userId);

}
