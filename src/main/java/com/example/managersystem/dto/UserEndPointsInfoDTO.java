package com.example.managersystem.dto;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * {@code @className:}      UserEndPointsInfoDTO
 * {@code @author:}     dengxiangtian
 * {@code @description:}  TODO
 * {@code @date:}    2024/4/26 10:56 PM
 */
@Data
public class UserEndPointsInfoDTO implements Serializable {
    private static final long serialVersionUID = -1450241234268540943L;

    private long userId;

    private List<String> endPoints;

}
