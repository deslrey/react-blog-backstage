package org.deslre.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.deslre.commons.result.Results;
import org.deslre.user.entity.dto.UserInfoDTO;
import org.deslre.user.entity.po.InvitationCodes;
import org.deslre.user.entity.vo.InviteCodeVO;

import java.util.List;

/**
 * ClassName: InvitationCodesService
 * Description: 邀请码服务层
 * Author: Deslrey
 * Date: 2025-06-08 20:07
 * Version: 1.0
 */
public interface InvitationCodesService  extends IService<InvitationCodes> {

    Results<Void> addInviteCode(UserInfoDTO userInfoDTO,InviteCodeVO vo);

    Results<List<InviteCodeVO>> inviteCodeList();

    Results<Void> updateInviteCode(InviteCodeVO vo);

    Results<Void> updateExist(Integer id, String code, Boolean exist);
}