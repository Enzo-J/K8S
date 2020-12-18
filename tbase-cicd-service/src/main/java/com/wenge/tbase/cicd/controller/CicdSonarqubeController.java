package com.wenge.tbase.cicd.controller;


import com.wenge.tbase.cicd.controller.service.SonarqubeControllerService;
import com.wenge.tbase.cicd.entity.param.CreateAndUpdateSonarqubeParam;
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
@Api(description = "sonarqube文件管理API接口", tags = "sonarqube文件管理API接口")
@RestController
public class CicdSonarqubeController {

    @Resource
    private SonarqubeControllerService service;

    @ApiOperation(value = "获取sonarqube文件列表")
    @GetMapping(value = "/sonarqubeList")
    public ResultVO getSonarqubeList(@RequestParam(required = false) String name,
                                     @RequestParam Integer current,
                                     @RequestParam Integer size) {
        if (current == null) {
            current = 0;
        }
        if (size == null || size == 0) {
            size = 10;
        }
        return new ResultVO(ResultCode.SUCCESS, service.getSonarqubeList(name, current, size));
    }

    @ApiOperation(value = "添加sonarqube文件")
    @PostMapping(value = "/sonarqube")
    public ResultVO createSonarqube(@RequestBody CreateAndUpdateSonarqubeParam param) {
        if (param == null || param.getName() == null || param.getContent() == null) {
            return new ResultVO(ResultCode.PARAM_IS_EMPTY);
        }
        if (!service.judgeSonarqubeExist(param.getName())) {
            return new ResultVO(1001, "名称已存在", false);
        }
        return new ResultVO(ResultCode.SUCCESS, service.createSonarqube(param));
    }

    @ApiOperation(value = "修改sonarqube文件")
    @PutMapping(value = "/sonarqube")
    public ResultVO updateSonarqube(@RequestBody CreateAndUpdateSonarqubeParam param) {
        if (param == null || param.getId() == null || param.getName() == null || param.getContent() == null) {
            return new ResultVO(ResultCode.PARAM_IS_EMPTY);
        }
        if (!service.judgeSonarqubeExist(param.getName())) {
            return new ResultVO(1001, "名称已存在", false);
        }
        return new ResultVO(ResultCode.SUCCESS, service.updateSonarqube(param));
    }

    @ApiOperation(value = "删除sonarqube文件")
    @DeleteMapping(value = "/sonarqube/{id}")
    public ResultVO deleteSonarqube(@PathVariable Long id) {
        if (id == null) {
            return new ResultVO(ResultCode.PARAM_IS_EMPTY);
        }
        return new ResultVO(ResultCode.SUCCESS, service.deleteSonarqube(id));
    }

    @ApiOperation(value = "根据名称判断是否存在sonarqube文件")
    @GetMapping(value = "/judgeSonarqubeExist")
    public ResultVO judgeSonarqubeExist(@RequestParam String name) {
        if (name == null) {
            return new ResultVO(ResultCode.PARAM_IS_EMPTY);
        }
        return new ResultVO(ResultCode.SUCCESS, service.judgeSonarqubeExist(name));
    }
}

