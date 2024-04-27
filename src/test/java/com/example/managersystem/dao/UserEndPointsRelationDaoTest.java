package com.example.managersystem.dao;

import com.example.managersystem.model.UserEndPointsRelationEntity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collections;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;

@SpringBootTest
public class UserEndPointsRelationDaoTest {

    private UserEndPointsRelationDao userEndPointsRelationDao;
    private String tempDataFilePath;

    @Before
    public void setUp() throws IOException {
        userEndPointsRelationDao = new UserEndPointsRelationDao();
        // 创建临时文件模拟数据文件
        File tempFile = File.createTempFile("testUserEndPoints", ".json");
        tempDataFilePath = tempFile.getAbsolutePath();
        userEndPointsRelationDao.dataFilePath = tempDataFilePath;
    }

    @After
    public void tearDown() {
        // 测试完成后删除临时文件
        new File(tempDataFilePath).delete();
    }

    /**
     * 测试 findByUserId 方法，当用户不存在时
     */
    @Test
    public void testFindByUserIdWhenUserDoesNotExist() {

        long userId = 1L;


        Optional<UserEndPointsRelationEntity> result = userEndPointsRelationDao.findByUserId(userId);


        assertFalse(result.isPresent());
    }

    /**
     * 测试 update 方法，添加新用户
     */
    @Test
    public void testUpdateAddNewUser() throws IOException {

        long userId = 1L;
        UserEndPointsRelationEntity entity = new UserEndPointsRelationEntity();
        entity.setUserId(userId);
        entity.setEndPoints(Lists.newArrayList("1", "2"));


        userEndPointsRelationDao.update(entity);
        Optional<UserEndPointsRelationEntity> queryRes = userEndPointsRelationDao.findByUserId(userId);


        ObjectMapper objectMapper = new ObjectMapper();
        Map<Long, UserEndPointsRelationEntity> dataMap = objectMapper.readValue(new File(tempDataFilePath), new TypeReference<Map<Long, UserEndPointsRelationEntity>>() {});
        assertTrue(dataMap.containsKey(userId));
        assertEquals(entity, dataMap.get(userId));
    }
}
