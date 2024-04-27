package com.example.managersystem.mapper;

import com.example.managersystem.dto.UserEndPointsInfoDTO;
import com.example.managersystem.model.UserEndPointsRelationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * {@code @className:}      UserEndPointsInfoMapper
 * {@code @author:}     dengxiangtian
 * {@code @description:}  TODO
 * {@code @date:}    2024/4/26 11:01 PM
 */
@Mapper
public interface UserEndPointsInfoDTOMapper {
    UserEndPointsInfoDTOMapper INSTANCE = Mappers.getMapper( UserEndPointsInfoDTOMapper.class );

    UserEndPointsRelationEntity toUserEndPointsRelationEntity(UserEndPointsInfoDTO userEndPointsRelationDTO);
}
