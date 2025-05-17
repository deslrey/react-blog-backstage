package org.deslre.user.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * ClassName: ArticleDraftConvert
 * Description: ArticleDraft类转换
 * Author: Deslrey
 * Date: 2025-05-17 19:15
 * Version: 1.0
 */
@Mapper(componentModel = "spring")
public interface ArticleDraftConvert {

    ArticleDraftConvert INSTANCE = Mappers.getMapper(ArticleDraftConvert.class);
}
