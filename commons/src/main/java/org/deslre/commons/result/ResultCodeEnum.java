package org.deslre.commons.result;

import lombok.Getter;

/**
 * ClassName: ResultCodeEnum
 * Description: 统一返回结果状态信息类
 * Author: Deslrey
 * Date: 2025-03-28 13:18
 * Version: 1.0
 */

@Getter
public enum ResultCodeEnum {

    SUCCESS(200, "成功"),
    FAIL(201, "失败"),
    SERVICE_ERROR(2012, "服务异常"),
    DATA_ERROR(204, "数据异常"),
    ILLEGAL_REQUEST(205, "非法请求"),
    REPEAT_SUBMIT(206, "重复提交"),
    ARGUMENT_VALID_ERROR(210, "参数校验异常"),

    LOGIN_AUTH(208, "未登陆"),
    PERMISSION(209, "没有权限"),
    ACCOUNT_ERROR(214, "账号不正确"),
    PASSWORD_ERROR(215, "密码不正确"),
    LOGIN_MOBILE_ERROR(216, "账号不正确"),
    ACCOUNT_STOP(217, "账号已停用"),
    NODE_ERROR(218, "该节点下有子节点，不可以删除");

    private final Integer code;
    private final String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
