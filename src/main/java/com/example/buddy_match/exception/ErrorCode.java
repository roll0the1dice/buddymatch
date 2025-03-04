package com.example.buddy_match.exception;

/**
 * This is a generated ErrorCode for demonstration purposes.
 */
public enum ErrorCode {

     SUCCESS(0, "ok"), PARAMS_ERROR(40000, "请求参数错误"),  NOT_LOGIN_ERROR(40100, "未登录"), NO_AUTH_ERROR(40101, "无权限"), NOT_FOUND_ERROR(40400, "请求数据不存在"), FORBIDDEN_ERROR(40300, "禁止访问"), SYSTEM_ERROR(50000, "系统内部异常"), OPERATION_ERROR(50001, "操作失败");

    /** This is an example ErrorCode. */
    private String statusCode;

    /** This is an example ErrorCode. */
    private Integer statusCodeValue;

    ErrorCode() {
    }

    ErrorCode(Integer statusCodeValue, String statusCode) {
        this.statusCodeValue = statusCodeValue;
        this.statusCode = statusCode;
    }

    String getStatusCode() {
        return statusCode;
    }

    Integer getStatusCodeValue() {
        return statusCodeValue;
    }

    @Override
    public String toString() {
        return String.format("Code = %s, Message = %s",statusCodeValue,statusCode);
    }
}