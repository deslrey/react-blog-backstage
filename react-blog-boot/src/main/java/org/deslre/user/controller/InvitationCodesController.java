package org.deslre.user.controller;

import org.deslre.annotation.AuthCheck;
import org.deslre.commons.result.Results;
import org.deslre.user.entity.dto.UserInfoDTO;
import org.deslre.user.entity.vo.InviteCodeVO;
import org.deslre.user.service.InvitationCodesService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ClassName: InvitationCodesController
 * Description: 邀请你控制层
 * Author: Deslrey
 * Date: 2025-06-08 20:05
 * Version: 1.0
 */
@RestController
@RequestMapping("/invitation-codes")
public class InvitationCodesController extends BaseController {

    @Resource
    private InvitationCodesService invitationCodesService;

    @PostMapping("addInviteCode")
    @AuthCheck(admin = true, checkLogin = true, log = "添加验证码", category = "inviteCode")
    public Results<Void> addInviteCode(HttpServletRequest request, String inviteCode, String remark, Boolean isAdmin) {
        UserInfoDTO userInfoDTO = parseUserInfo(request);
        return invitationCodesService.addInviteCode(userInfoDTO, inviteCode, remark, isAdmin);
    }

    @GetMapping("inviteCodeList")
    @AuthCheck(admin = true, checkLogin = true, log = "获取邀请码列表", category = "inviteCode")
    public Results<List<InviteCodeVO>> inviteCodeList(HttpServletRequest request) {
        return invitationCodesService.inviteCodeList();
    }
}
