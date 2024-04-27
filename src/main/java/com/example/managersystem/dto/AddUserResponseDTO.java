package com.example.managersystem.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * {@code @className:}      AddUserResponseDTO
 * {@code @author:}     dengxiangtian
 * {@code @description:}  TODO
 * {@code @date:}    2024/4/26 9:39 PM
 */
@Data
public class AddUserResponseDTO implements Serializable {
    private static final long serialVersionUID = -4555273533957866234L;

    String operationResult;
}
