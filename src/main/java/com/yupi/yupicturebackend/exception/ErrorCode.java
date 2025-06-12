package com.yupi.yupicturebackend.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    SUCCESS(0, "Ok"),
    PARAMS_ERROR(40000, "Request parameter error"),
    NOT_LOGIN_ERROR(40100, "Not login"),
    NO_AUTH_ERROR(40101, "No permission"),
    NOT_FOUND_ERROR(40400, "Request data does not exist"),
    FORBIDDEN_ERROR(40300, "Access prohibited"),
    SYSTEM_ERROR(50000, "Internal system exception"),
    OPERATION_ERROR(50001, "Operation failed");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
