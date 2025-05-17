package org.deslre.user.service;

import org.deslre.commons.result.Results;
import org.deslre.user.entity.po.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2025-04-24
 */
public interface UserService extends IService<User> {

    Results<User> login(String username, String password);
}
