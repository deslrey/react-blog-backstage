package org.deslre.user.service;

import org.deslre.commons.result.Results;
import org.deslre.user.entity.po.Users;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2025-06-08
 */
public interface UsersService extends IService<Users> {

    Results<Void> register(String email, String inviteCode);
}
