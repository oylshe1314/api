package com.sk.op.api.common.exception;


public class StandardResponseException extends RuntimeException implements ResponseException {

    private final int status;

    public StandardResponseException() {
        super(ResponseStatus.UNKNOWN.getMessage());
        this.status = ResponseStatus.UNKNOWN.getStatus();
    }

    public StandardResponseException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
        this.status = responseStatus.getStatus();
    }

    public  StandardResponseException(String message) {
        super(message);
        this.status = ResponseStatus.FAILED.getStatus();
    }

    public StandardResponseException(int status, String message) {
        super(message);
        this.status = status;
    }

    public StandardResponseException(Throwable cause) {
        ResponseException responseException = ResponseException.extractResponseException(cause);
        if (responseException != null) {
            this.status = responseException.getStatus();
        } else {
            this.status = ResponseStatus.UNKNOWN.getStatus();
        }
    }

    @Override
    public int getStatus() {
        return this.status;
    }
}
