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
 * @since 2025-04-18
 */
public interface UserService extends IService<User> {

    Results<UserVO> userLogin(String userName, String passWord);

    Results<UserVO> register(String userName, String passWord);
}
