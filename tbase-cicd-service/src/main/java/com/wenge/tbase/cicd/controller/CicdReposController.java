package com.wenge.tbase.cicd.controller;


import com.wenge.tbase.cicd.controller.service.ReposControllerService;
import com.wenge.tbase.cicd.entity.param.CreateAndUpdateReposParam;
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
 * @since 2020-12-09
 */
@Api(description = "代码仓库配置API接口", tags = "代码仓库配置API接口")
@RestController
public class CicdReposController {

    @Resource
    private ReposControllerService service;

    @ApiOperation(value = "获取代码仓库信息列表")
    @GetMapping(value = "/reposList")
    public ResultVO getReposList(@RequestParam(required = false) String name,
                                 @RequestParam Integer current,
                                 @RequestParam Integer size) {
        if (current == null) {
            current = 0;
        }
        if (size == null || size == 0) {
            size = 10;
        }
        return new ResultVO(ResultCode.SUCCESS, service.getReposList(name, current, size));
    }

    @ApiOperation(value = "创建代码仓库信息")
    @PostMapping(value = "/repos")
    public ResultVO createRepos(@RequestBody CreateAndUpdateReposParam param) {
        if (param == null || param.getName() == null || param.getProjectName() == null || param.getCredentialId() == null) {
            return new ResultVO(ResultCode.PARAM_IS_EMPTY);
        }
        if (!service.judgeReposExist(param.getName(), param.getId())) {
            return new ResultVO(1001, "名称已存在", false);
        }
        return new ResultVO(ResultCode.SUCCESS, service.createRepos(param));
    }

    @ApiOperation(value = "修改代码仓库信息")
    @PutMapping(value = "/repos")
    public ResultVO updateRepos(@RequestBody CreateAndUpdateReposParam param) {
        if (param == null || param.getName() == null || param.getProjectName() == null || param.getCredentialId() == null || param.getId() == null) {
            return new ResultVO(ResultCode.PARAM_IS_EMPTY);
        }
        if (service.judgeReposExist(param.getName(), param.getId())) {
            return new ResultVO(1001, "名称已存在", false);
        }
        return new ResultVO(ResultCode.SUCCESS, service.updateRepos(param));
    }

    @ApiOperation(value = "删除代码仓库信息")
    @DeleteMapping(value = "/repos/{id}")
    public ResultVO deleteRepos(@PathVariable Long id) {
        if (id == null) {
            return new ResultVO(ResultCode.PARAM_IS_EMPTY);
        }
        return new ResultVO(ResultCode.SUCCESS, service.deleteRepos(id));
    }

    @ApiOperation(value = "根据名称判断是否存在代码仓库")
    @GetMapping(value = "/judgeReposExist")
    public ResultVO judgeReposExist(@RequestParam String name) {
        if (name == null) {
            return new ResultVO(ResultCode.PARAM_IS_EMPTY);
        }
        return new ResultVO(ResultCode.SUCCESS, service.judgeReposExist(name, null));
    }
}

