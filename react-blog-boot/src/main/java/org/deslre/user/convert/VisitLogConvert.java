package org.deslre.user.convert;

import org.deslre.user.entity.po.VisitLog;
import org.deslre.user.entity.po.Visitor;
import org.deslre.user.entity.vo.VisitLogVO;
import org.deslre.user.entity.vo.VisitorVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * ClassName: VisitLogConvert
 * Description: 访客日志转换类
 * Author: Deslrey
 * Date: 2025-06-03 10:35
 * Version: 1.0
 */
@Mapper(componentModel = "spring")
public interface VisitLogConvert {

    VisitLogConvert INSTANCE = Mappers.getMapper(VisitLogConvert.class);

    VisitLog convert(VisitLogVO vo);

    VisitLogVO convert(VisitLog visitor);

    List<VisitLog> convertList(List<VisitLogVO> list);

    List<VisitLogVO> convertListVO(List<VisitLog> list);

}
