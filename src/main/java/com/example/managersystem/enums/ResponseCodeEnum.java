package com.example.managersystem.enums;

public enum ResponseCodeEnum {
    SUCCESS(200, "success"),
    PARAM_ERROR(400, "param error"),
    UNAUTHORIZED(401, "unauthorized"),
    NOT_FOUND(404, "page not found"),
    INTERNAL_SERVER_ERROR(500, "internal error");

    private final int code;
    private final String desc;

    ResponseCodeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
