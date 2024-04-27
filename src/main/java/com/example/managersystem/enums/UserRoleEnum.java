package com.example.managersystem.enums;


import org.springframework.lang.Nullable;

/**
 * User Role Enum
 */
public enum UserRoleEnum {
    ADMIN("admin", 1),
    USER("user", 2);

    final private String desc;
    final private int code;

    UserRoleEnum(String desc, int code) {
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public int getCode() {
        return code;
    }

    @Nullable
    public static UserRoleEnum getByCode(int code) {
        for (UserRoleEnum userRoleEnum : UserRoleEnum.values()) {
            if (userRoleEnum.getCode() == code) {
                return userRoleEnum;
            }
        }
        return null;
    }

    @Nullable
    public static UserRoleEnum getByDesc(String desc) {
        for (UserRoleEnum userRoleEnum : UserRoleEnum.values()) {
            if (userRoleEnum.getDesc().equals(desc)) {
                return userRoleEnum;
            }
        }
        return null;
    }
}
