package org.deslre.user.convert;

import org.deslre.user.entity.po.User;
import org.deslre.user.entity.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * ClassName: UserConvert
 * Description: 用户实体类转换
 * Author: Deslrey
 * Date: 2025-06-10 10:22
 * Version: 1.0
 */
@Mapper(componentModel = "spring")
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    UserVO convert(User user);

    User convert(UserVO vo);
}
