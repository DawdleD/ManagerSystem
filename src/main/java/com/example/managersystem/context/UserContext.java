package com.example.managersystem.context;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import lombok.Data;

/**
 * {@code @className:}      UserContext
 * {@code @author:}     dengxiangtian
 * {@code @description:}  TODO
 * {@code @date:}    2024/4/27 1:02 AM
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserContext implements Serializable {
    private static final long serialVersionUID = 4399308508816624055L;
    private Long userId;
    private String accountName;
    private String role;
}