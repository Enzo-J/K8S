package com.wenge.tbase.cicd.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.wenge.tbase.cicd.controller.service.CredentialControllerService;
import com.wenge.tbase.cicd.entity.param.CreateAndUpdateCredentialParam;
import com.wenge.tbase.commons.result.ResultCode;
import com.wenge.tbase.commons.result.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Wang XingPeng
 * @since 2020-11-27
 */
@Api(description = "凭证管理API接口", tags = "凭证管理API接口")
@RestController
public class CicdCredentialController {

    @Resource
    private CredentialControllerService credentialService;

    @ApiOperation(value = "查询凭证列表")
    @GetMapping(value = "/credentialList")
    public ResultVO getCredentialList(@RequestParam(required = false) String username,
                                      @RequestParam Integer current,
                                      @RequestParam Integer size) {
        if (current == null) {
            current = 0;
        }
        if (size == null || size == 0) {
            size = 10;
        }
        return new ResultVO(ResultCode.SUCCESS, credentialService.getCredentialList(username, current, size));
    }

    @ApiOperation(value = "创建凭证")
    @PostMapping(value = "/credential")
    public ResultVO createCredential(@RequestBody CreateAndUpdateCredentialParam param) {
        if (param == null || param.getType() == null || param.getUsername() == null) {
            return new ResultVO(ResultCode.PARAM_IS_EMPTY);
        }
        if (!credentialService.judgeCredentialExist(param.getUsername())) {
            return new ResultVO(1001, "用户名已存在", false);
        }
        return new ResultVO(ResultCode.SUCCESS, credentialService.createCredential(param));
    }

    @ApiOperation(value = "修改凭证")
    @PutMapping(value = "/credential")
    public ResultVO updateCredential(@RequestBody CreateAndUpdateCredentialParam param) {
        if (param == null || param.getId() == null || param.getCredentialId() == null || param.getType() == null || param.getUsername() == null) {
            return new ResultVO(ResultCode.PARAM_IS_EMPTY);
        }
        if (!credentialService.judgeCredentialExist(param.getUsername())) {
            return new ResultVO(1001, "用户名已存在", false);
        }
        return new ResultVO(ResultCode.SUCCESS, credentialService.updateCredential(param));
    }

    @ApiOperation(value = "删除凭证")
    @DeleteMapping(value = "/credential/{credentialId}")
    public ResultVO deleteCredential(@PathVariable String credentialId) {
        if (credentialId == null) {
            return new ResultVO(ResultCode.PARAM_IS_EMPTY);
        }
        return new ResultVO(ResultCode.SUCCESS, credentialService.deleteCredential(credentialId));
    }

    @ApiOperation(value = "根据用户名判断是否存在数据")
    @GetMapping(value = "/judgeCredentialExist")
    public ResultVO judgeCredentialExist(@RequestParam String username) {
        if (username == null) {
            return new ResultVO(ResultCode.PARAM_IS_EMPTY);
        }
        return new ResultVO(ResultCode.SUCCESS, credentialService.judgeCredentialExist(username));
    }
}

