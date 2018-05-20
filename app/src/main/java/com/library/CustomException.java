package com.library;


import com.mvp.app.model.dto.response.BaseResponse;

public class CustomException extends Exception {

    private int code;
    private String exception;
    private String message;

    public CustomException(int code, String exception) {
        this.code = code;
        this.exception = exception;
    }

    public CustomException(int code, Throwable throwable) {
        this.code = code;
        this.exception = throwable.getMessage();
    }

    public CustomException(int code, BaseResponse response) {
        this.code = code;
        this.exception = response.getError();
        this.message = response.getMessage();
    }

    public CustomException(BaseResponse response) {
        ExceptionTracker.track(response.getError());
        this.exception = response.getError();
        this.message = response.getMessage();
    }

    public CustomException() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
