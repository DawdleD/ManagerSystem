package com.example.managersystem.controller;

import com.example.managersystem.annotation.PermissionCheck;
import com.example.managersystem.context.UserContext;
import com.example.managersystem.dto.AddUserRequestDTO;
import com.example.managersystem.dto.AddUserResponseDTO;
import com.example.managersystem.dto.CommonResponse;
import com.example.managersystem.dto.UserEndPointsInfoDTO;
import com.example.managersystem.enums.ResponseCodeEnum;
import com.example.managersystem.enums.UserRoleEnum;
import com.example.managersystem.manage.UserEndPointsManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code @className:}      AdminController
 * {@code @author:}     dengxiangtian
 * {@code @description:}  AdminController
 * {@code @date:}    2024/4/26 9:19 PM
 */
@RequestMapping("/admin")
@RestController
@Slf4j
public class AdminController
{
    @Resource
    private UserEndPointsManager userEndPointsManager;

    private final static String OPERATION_SUCCEED = "Operation succeeded";
    private final static String OPERATION_FAILURE = "Operation failed, please try later";


    @PostMapping("/addUser")
    @PermissionCheck({UserRoleEnum.ADMIN})
    public CommonResponse<AddUserResponseDTO> addUser(@RequestBody @Validated AddUserRequestDTO addUserRequestDTO,
            @RequestAttribute("userContext") UserContext userContext) {
        log.info("addUser request:{}, context={}", addUserRequestDTO, userContext);
        try {
            AddUserResponseDTO addUserResponseDTO = new AddUserResponseDTO();
            UserEndPointsInfoDTO userEndPointsInfoDTO = tryConvertToUserEndPointsInfoDTO(addUserRequestDTO);
            boolean operationRes = userEndPointsManager.addUserEndPointsRelation(userEndPointsInfoDTO);
            addUserResponseDTO.setOperationResult(operationRes ? OPERATION_SUCCEED : OPERATION_FAILURE);
            return CommonResponse.buildSuccessRespWithData(addUserResponseDTO);
        }
        catch (IllegalArgumentException e) {
            return CommonResponse.buildFailResp(ResponseCodeEnum.PARAM_ERROR);
        }
        catch (Exception e) {
            log.error("addUser error", e);
            return CommonResponse.buildFailResp(ResponseCodeEnum.INTERNAL_SERVER_ERROR);
        }
    }

    protected UserEndPointsInfoDTO tryConvertToUserEndPointsInfoDTO(AddUserRequestDTO addUserRequestDTO) {
        UserEndPointsInfoDTO userEndPointsInfoDTO = new UserEndPointsInfoDTO();
        userEndPointsInfoDTO.setUserId(addUserRequestDTO.getUserId());
        userEndPointsInfoDTO.setEndPoints(new ArrayList<>());
        if (CollectionUtils.isEmpty(addUserRequestDTO.getEndpoint())) {
            return userEndPointsInfoDTO;
        }
        List<String> extractedList = addUserRequestDTO.getEndpoint().stream().map(
                endPoint -> {
                    if (endPoint.matches("resource .*")) {
                        return endPoint.substring(endPoint.indexOf(" ") + 1);
                    }
                    throw new IllegalArgumentException("Invalid end point format");
                }
        ).collect(Collectors.toList());
        userEndPointsInfoDTO.setEndPoints(extractedList);
        return userEndPointsInfoDTO;
    }
}
