package com.example.managersystem.dto;

import com.example.managersystem.enums.ResponseCodeEnum;
import java.io.Serializable;
import lombok.Data;

/**
 * {@code @className:}      CommonResponse
 * {@code @author:}     dengxiangtian
 * {@code @description:}  Common Response class
 * {@code @date:}    2024/4/26 9:33 PM
 */
@Data
public class CommonResponse<T> implements Serializable {
    private static final long serialVersionUID = -5006297824762032973L;
    private final int code;
    private final String desc;
    private T data;

    private CommonResponse(int code, String desc, T data) {
        this.code = code;
        this.desc = desc;
        this.data = data;
    }

    private CommonResponse(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static <T> CommonResponse<T> buildSuccessRespWithData(T data) {
        return new CommonResponse<>(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getDesc(), data);
    }

    public static <T> CommonResponse<T> buildFailResp(ResponseCodeEnum codeEnum) {
        return new CommonResponse<>(codeEnum.getCode(), codeEnum.getDesc());
    }
}