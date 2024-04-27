package com.example.managersystem.manage;

import com.example.managersystem.dao.UserEndPointsRelationDao;
import com.example.managersystem.dto.UserEndPointsInfoDTO;
import com.example.managersystem.mapper.UserEndPointsInfoDTOMapper;
import com.example.managersystem.mapper.UserEndPointsInfoEntityMapper;
import com.example.managersystem.model.UserEndPointsRelationEntity;
import java.util.Optional;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * {@code @className:}      UserEndPointsManager
 * {@code @author:}     dengxiangtian
 * {@code @description:}  TODO
 * {@code @date:}    2024/4/26 10:09 PM
 */
@Component
@Slf4j
public class UserEndPointsManager {

    @Resource
    private UserEndPointsRelationDao userEndPointsRelationDao;

    public boolean addUserEndPointsRelation(UserEndPointsInfoDTO infoDTO) {
        if (infoDTO == null) {
            throw new IllegalArgumentException("empty userEndPointsInfo");
        }
        try {
            return userEndPointsRelationDao.update(UserEndPointsInfoDTOMapper.INSTANCE.toUserEndPointsRelationEntity(infoDTO));
        } catch (Exception e) {
            log.error("addUserEndPointsRelation failure", e);
            return false;
        }
    }

    public Optional<UserEndPointsInfoDTO> getUserEndPointsInfo(long userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("invalid userId");
        }
        try {
            Optional<UserEndPointsRelationEntity> retEntity = (userEndPointsRelationDao.findByUserId(userId));
            if (!retEntity.isPresent()) {
                return Optional.empty();
            }
            return Optional.of(UserEndPointsInfoEntityMapper.INSTANCE.toUserEndPointsInfoDTO(retEntity.get()));
        }
        catch (Exception e) {
            throw new RuntimeException("query failure");
        }
    }
}
