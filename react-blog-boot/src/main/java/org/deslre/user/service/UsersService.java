package org.deslre.user.service;

import org.deslre.commons.result.Results;
import org.deslre.user.entity.po.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.deslre.user.entity.vo.UserVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2025-06-08
 */
public interface UsersService extends IService<User> {

    Results<Void> register(String userName, String email, String passWord, String inviteCode);

    Results<UserVO> login(String email, String passWord);

    Results<Void> block(Integer userId, Boolean exist);

    Results<Void> sendEmailCode(String email);
}
