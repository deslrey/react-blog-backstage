package org.deslre.user.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * ClassName: UserConvert
 * Description: User类转换
 * Author: Deslrey
 * Date: 2025-04-19 17:16
 * Version: 1.0
 */
@Mapper(componentModel = "spring")
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);


}
