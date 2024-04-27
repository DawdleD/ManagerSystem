package com.example.managersystem.controller;

import com.example.managersystem.annotation.PermissionCheck;
import com.example.managersystem.context.UserContext;
import com.example.managersystem.dto.CommonResponse;
import com.example.managersystem.dto.ResourceVisitResponseDTO;
import com.example.managersystem.dto.UserEndPointsInfoDTO;
import com.example.managersystem.enums.ResponseCodeEnum;
import com.example.managersystem.enums.UserRoleEnum;
import com.example.managersystem.manage.UserEndPointsManager;
import java.util.Optional;
import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code @className:}      UserController
 * {@code @author:}     dengxiangtian
 * {@code @description:}  TODO
 * {@code @date:}    2024/4/26 10:49 PM
 */
@RestController
@Slf4j
public class UserController {

    protected static final String VISIT_PERMITTED_MSG = "visit resource permitted";

    protected static final String VISIT_DENIED_MSG = "visit denied";

    @Resource
    private UserEndPointsManager userEndPointsManager;



    /**
     * 根据资源类型和用户ID获取资源信息
     * @param resource 资源类型，从URL路径中获取
     * @return 返回资源信息
     */
    @GetMapping("/user/{resource}")
    @PermissionCheck({UserRoleEnum.ADMIN, UserRoleEnum.USER})
    public CommonResponse<ResourceVisitResponseDTO> getResourceByUserId(@Validated @NotNull @PathVariable("resource") String resource, @RequestAttribute("userContext") UserContext userContext) {
        log.info("getResourceByUserId called for resource {}, context={}", resource, userContext);
        long userId = userContext.getUserId();
        try {
            if (userId > 0) {
                Optional<UserEndPointsInfoDTO> userRight = (userEndPointsManager.getUserEndPointsInfo(userId));
                if (userRight.isPresent() && userRight.get().getEndPoints().contains(resource)) {
                    ResourceVisitResponseDTO responseDTO = new ResourceVisitResponseDTO();
                    responseDTO.setMessage(VISIT_PERMITTED_MSG);
                    log.info("getResourceByUserId permitted, userid={}, resource={}", userId, resource);
                    return CommonResponse.buildSuccessRespWithData(responseDTO);
                }
            }
            log.info("getResourceByUserId denied, userid={}, resource={}", userId, resource);
            ResourceVisitResponseDTO responseDTO = new ResourceVisitResponseDTO();
            responseDTO.setMessage(VISIT_DENIED_MSG);
            return CommonResponse.buildSuccessRespWithData(responseDTO);
        } catch (Exception e) {
            log.error("getResourceByUserId error, userid={}, resource={}", userId, resource, e);
        }
        return CommonResponse.buildFailResp(ResponseCodeEnum.INTERNAL_SERVER_ERROR);
    }
}