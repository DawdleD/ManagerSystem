package com.example.managersystem.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * {@code @className:}      UserEndPointsRelationEntity
 * {@code @author:}     dengxiangtian
 * {@code @description:}  TODO
 * {@code @date:}    2024/4/26 10:16 PM
 */
@Data
public class UserEndPointsRelationEntity implements Serializable {
    private static final long serialVersionUID = 7403508515970054992L;
    private long userId;
    private List<String> endPoints;
}
