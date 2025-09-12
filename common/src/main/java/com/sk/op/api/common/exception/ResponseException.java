package com.sk.op.api.common.exception;

import com.sk.op.api.common.dto.ResponseDto;

public interface ResponseException {

    int getStatus();

    String getMessage();

    default ResponseDto<?> response() {
        return ResponseDto.fail(getStatus(), getMessage());
    }

    static ResponseException extractResponseException(Throwable cause) {
        while (cause != null) {
            if (cause instanceof ResponseException) {
                return (ResponseException) cause;
            }
            cause = cause.getCause();
        }
        return null;
    }
}
