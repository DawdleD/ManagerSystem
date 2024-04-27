package com.example.managersystem.dto;

import java.io.Serializable;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * {@code @className:}      AddUserRequestDTO
 * {@code @author:}     dengxiangtian
 * {@code @description:}  addUserRequest
 * {@code @date:}    2024/4/26 9:15 PM
 */
@Data
public class AddUserRequestDTO implements Serializable {

    private static final long serialVersionUID = 164983766948454227L;

    @NotNull(message = "userId should not be null")
    private Long userId;

    private List<String> endpoint;
}
