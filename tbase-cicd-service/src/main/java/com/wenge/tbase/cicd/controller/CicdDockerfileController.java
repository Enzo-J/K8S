package com.wenge.tbase.cicd.controller;


import com.wenge.tbase.cicd.controller.service.DockerfileControllerService;
import com.wenge.tbase.cicd.entity.param.CreateAndUpdateDockerfileParam;
import com.wenge.tbase.commons.result.ResultCode;
import com.wenge.tbase.commons.result.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Wang XingPeng
 * @since 2020-11-30
 */
@Api(description = "dockerfile管理API接口")
@RestController
public class CicdDockerfileController {

    @Resource
    private DockerfileControllerService service;

    @ApiOperation(value = "获取dockerfile列表")
    @GetMapping(value = "/dockerfileList")
    public ResultVO getDockerfileList(@RequestParam(required = false) String name,
                                      @RequestParam Integer current,
                                      @RequestParam Integer size) {
        if (current == null) {
            current = 0;
        }
        if (size == null || size == 0) {
            size = 10;
        }
        return new ResultVO(ResultCode.SUCCESS, service.getDockerfileList(name, current, size));
    }

    @ApiOperation(value = "创建dockerfile文件")
    @PostMapping(value = "/dockerfile")
    public ResultVO createDockerfile(@RequestBody CreateAndUpdateDockerfileParam param) {
        if (param == null || param.getName() == null || param.getContent() == null) {
            return new ResultVO(ResultCode.PARAM_IS_EMPTY);
        }
        return new ResultVO(ResultCode.SUCCESS, service.createDockerfile(param));
    }

    @ApiOperation(value = "修改dockerfile文件")
    @PutMapping(value = "/dockerfile")
    public ResultVO updateDockerfile(@RequestBody CreateAndUpdateDockerfileParam param) {
        if (param == null || param.getName() == null || param.getContent() == null) {
            return new ResultVO(ResultCode.PARAM_IS_EMPTY);
        }
        return new ResultVO(ResultCode.SUCCESS, service.updateDockerfile(param));
    }

    @ApiOperation(value = "删除dockerfile文件")
    @DeleteMapping(value = "/dockerfile/{id}")
    public ResultVO deleteDockerfile(@PathVariable Long id) {
        if (id == null) {
            return new ResultVO(ResultCode.PARAM_IS_EMPTY);
        }
        return new ResultVO(ResultCode.SUCCESS, service.deleteDockerfile(id));
    }
}

