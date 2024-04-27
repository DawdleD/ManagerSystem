package com.example.managersystem.controller;

import com.example.managersystem.dto.AddUserRequestDTO;
import com.example.managersystem.dto.UserEndPointsInfoDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * {@code @className:}      AdminControllerMockTest
 * {@code @author:}     dengxiangtian
 * {@code @description:}  TODO
 * {@code @date:}    2024/4/27 9:37 AM
 */
@SpringBootTest
public class AdminControllerMockTest {
    @InjectMocks
    private AdminController adminController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * 测试 tryConvertToUserEndPointsInfoDTO 方法，当 endpoint 列表为空时
     */
    @Test
    public void testTryConvertToUserEndPointsInfoDTOWithEmptyEndpointList() {

        AddUserRequestDTO addUserRequestDTO = new AddUserRequestDTO();
        addUserRequestDTO.setUserId(1L);
        addUserRequestDTO.setEndpoint(Collections.emptyList());


        UserEndPointsInfoDTO result = adminController.tryConvertToUserEndPointsInfoDTO(addUserRequestDTO);


        assertEquals("userId check failed", 1L, result.getUserId());
        assertTrue("endPoints should be empty", result.getEndPoints().isEmpty());
    }

    /**
     * 测试 tryConvertToUserEndPointsInfoDTO 方法，当 endpoint 列表包含有效资源时
     */
    @Test
    public void testTryConvertToUserEndPointsInfoDTOWithValidEndpoints() {

        AddUserRequestDTO addUserRequestDTO = new AddUserRequestDTO();
        addUserRequestDTO.setUserId(1L);
        addUserRequestDTO.setEndpoint(Arrays.asList("resource 1", "resource 2"));


        UserEndPointsInfoDTO result = adminController.tryConvertToUserEndPointsInfoDTO(addUserRequestDTO);

        assertEquals("endPoints size error", 2, result.getEndPoints().size());
        assertTrue("endPoints should contain '1'", result.getEndPoints().contains("1"));
        assertTrue("endPoints should contain '2'", result.getEndPoints().contains("2"));
    }

    /**
     * 测试 tryConvertToUserEndPointsInfoDTO 方法，当 endpoint 列表包含无效格式资源时
     */
    @Test(expected = IllegalArgumentException.class)
    public void testTryConvertToUserEndPointsInfoDTOWithInvalidEndpoints() {

        AddUserRequestDTO addUserRequestDTO = new AddUserRequestDTO();
        addUserRequestDTO.setUserId(1L);
        addUserRequestDTO.setEndpoint(Collections.singletonList("invalid resource"));


        adminController.tryConvertToUserEndPointsInfoDTO(addUserRequestDTO);

    }
}
