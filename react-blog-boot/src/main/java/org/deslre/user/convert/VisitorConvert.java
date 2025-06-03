package org.deslre.user.convert;

import org.deslre.user.entity.po.Visitor;
import org.deslre.user.entity.vo.VisitorVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * ClassName: VisitorConvert
 * Description: 访客转换类
 * Author: Deslrey
 * Date: 2025-06-03 10:31
 * Version: 1.0
 */
@Mapper(componentModel = "spring")
public interface VisitorConvert {

    VisitorConvert INSTANCE = Mappers.getMapper(VisitorConvert.class);

    Visitor convert(VisitorVO vo);

    VisitorVO convert(Visitor visitor);

    List<Visitor> convertList(List<VisitorVO> list);

    List<VisitorVO> convertListVO(List<Visitor> list);
}
