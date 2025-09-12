package com.sk.op.api.common.exception;

import lombok.Getter;

@Getter
public enum ResponseStatus {

    //成功代码
    SUCCESSFUL(0, "成功"),

    //客户端错误
    FAILED(4000, "请求失败"),
    NOT_LOGGED(4001, "请先登录"),
    ACCESS_DENIED(4002, "权限不足"),
    TOKEN_INVALID(4003, "无效的TOKEN"),
    TOKEN_EXPIRED(4004, "TOKEN已过期"),
    METHOD_ERROR(4005, "方法错误"),
    PARAMETER_ERROR(4006, "参数错误"),

    //服务端错误
    UNKNOWN(5000, "未知错误"),
    ;

    private final int status;

    private final String message;

    ResponseStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ResponseStatus get(int status) {
        for (ResponseStatus value : ResponseStatus.values()) {
            if (value.getStatus() == status) {
                return value;
            }
        }
        return ResponseStatus.UNKNOWN;
    }
}
