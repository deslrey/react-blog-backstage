package org.deslre.user.convert;

import org.deslre.user.entity.po.InvitationCodes;
import org.deslre.user.entity.vo.InviteCodeVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * ClassName: InviteCodeConvert
 * Description: 邀请码实体类转换
 * Author: Deslrey
 * Date: 2025-06-12 20:37
 * Version: 1.0
 */
@Mapper(componentModel = "spring")
public interface InviteCodeConvert {
    InviteCodeConvert INSTANCE = Mappers.getMapper(InviteCodeConvert.class);

    InvitationCodes convert(InviteCodeVO vo);

    InviteCodeVO convert(InvitationCodes codes);

    List<InviteCodeVO> convert(List<InvitationCodes> invitationCodesList);

}
