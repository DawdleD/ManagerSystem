package com.example.managersystem.interceptors;

import com.example.managersystem.context.UserContext;
import com.example.managersystem.enums.UserRoleEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Base64;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

@SpringBootTest
public class UserContextInterceptorMockTest {

    @InjectMocks
    private UserContextInterceptor userContextInterceptor;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Value("${user.context.header}")
    private String header;

    private UserContext mockUserContext;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(userContextInterceptor, "header", header);
        mockUserContext = new UserContext();
        mockUserContext.setUserId(1L);
        mockUserContext.setAccountName("Test User");
        mockUserContext.setRole(UserRoleEnum.ADMIN.getDesc());
    }

    /**
     * 测试当请求头中包含有效的用户上下文信息时，预处理成功
     */
    @Test
    public void testPreHandleWithValidUserContext() throws Exception {
        UserContext userContext = new UserContext(); // 假设这是有效的用户上下文
        String userContextJson = new ObjectMapper().writeValueAsString(userContext);
        String encodedUserContext = Base64.getEncoder().encodeToString(userContextJson.getBytes());
        when(request.getHeader(header)).thenReturn(encodedUserContext);

        boolean result = userContextInterceptor.preHandle(request, response, null);

        assertTrue(result);
    }

    /**
     * 测试当请求头中缺少用户上下文信息时，抛出 IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPreHandleWithMissingUserContext() throws Exception {
        when(request.getHeader(header)).thenReturn(null);

        userContextInterceptor.preHandle(request, response, null);

    }

    /**
     * 测试当请求头中的用户上下文信息无效时，抛出 IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPreHandleWithInvalidUserContext() throws Exception {
        String invalidUserContext = "invalid";
        when(request.getHeader(header)).thenReturn(invalidUserContext);

        userContextInterceptor.preHandle(request, response, null);

    }
}
