package com.wenge.tbase.k8s.controller;

import com.wenge.tbase.commons.result.ResultCode;
import com.wenge.tbase.commons.result.ResultVO;
import com.wenge.tbase.k8s.bean.vo.K8SSecret;
import com.wenge.tbase.k8s.service.K8SService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;

@Api(tags = "应用管理平台Secret模块")
@RestController
@RequestMapping("/Screen/Secret")
@Validated
public class K8SSecretController {
    @Resource
    private K8SService k8SService;

    @ApiOperation("生成密文")
    @PostMapping("/createSecret")
    @ApiImplicitParams(@ApiImplicitParam(name = "K8SSecret", value = "K8SSecret对象", dataType = "K8SSecret"))
    public ResultVO createSecret(@RequestBody K8SSecret secretInfo) {
        String secretInfoType = secretInfo.getType();
        if (secretInfoType.equals("kubernetes.io/dockerconfigjson")) {
            if (secretInfo.getDockerSecret() == null || secretInfo.getDockerServer() == null || secretInfo.getDockerUsername() == null) {
                return new ResultVO(ResultCode.PARAM_IS_EMPTY);
            }
        }
        return k8SService.createSecret(secretInfo);
    }

    @ApiOperation("查看对应密文的详细信息")
    @GetMapping("/findSecretDetail")
    @ApiImplicitParams({@ApiImplicitParam(name = "secretName", value = "secret名称", dataType = "String", required =
            true),
            @ApiImplicitParam(name = "namespace", value = "名称空间", dataType = "String", required = true)})
    public ResultVO findSecretDetail(@NotBlank @RequestParam String secretName,
                                     @NotBlank @RequestParam String namespace) {
        return k8SService.findSecretDetail(secretName, namespace);
    }

    @ApiOperation("查询当前名称空间下所有密文")
    @GetMapping("/listSecret")
    @ApiImplicitParams(@ApiImplicitParam(name = "namespace", value = "名称空间", dataType = "String", required = true))
    public ResultVO listSecret(@NotBlank @RequestParam String namespace) {
        return k8SService.listSecret(namespace);
    }

    @ApiOperation("修改密文")
    @PutMapping("/editSecret")
    @ApiImplicitParams(@ApiImplicitParam(name = "K8SSecret", value = "K8SSecret对象", dataType = "K8SSecret"))
    public ResultVO editSecret(@RequestBody K8SSecret secretInfo) {
        return k8SService.editSecret(secretInfo);
    }

    @ApiOperation("删除单个密文")
    @PostMapping("/delSecret")
    @ApiImplicitParams({@ApiImplicitParam(name = "spacename", value = "命名空间名称", dataType = "String", required = true),
            @ApiImplicitParam(name = "name", value = "密文名称", dataType = "String", required = true)})
    public ResultVO delSecret(@NotBlank @RequestParam String spacename, @NotBlank @RequestParam String name) {
        return k8SService.delSecret(spacename, name);
    }

    @ApiOperation("批量删除密文")
    @PostMapping("/delSecrets")
    @ApiImplicitParams({@ApiImplicitParam(name = "spacename", value = "命名空间名称", dataType = "String", required = true),
            @ApiImplicitParam(name = "secretnames", value = "多个密文名称", dataType = "String", required = true)})
    public ResultVO delSecrets(@NotBlank @RequestParam String spacename, @NotBlank @RequestParam String secretnames) {
        String[] secretnamesArr = secretnames.split(",", -1);
        List<String> secretnamesList = Arrays.asList(secretnamesArr);
        return k8SService.delSecrets(spacename, secretnamesList);
    }

    @ApiOperation("删除该命名空间下的所有密文")
    @PostMapping("/delSecretsAll")
    @ApiImplicitParams(@ApiImplicitParam(name = "spacename", value = "命名空间名称", dataType = "String", required = true))
    public ResultVO delSecretsAll(@NotBlank @RequestParam String spacename) {
        return k8SService.delSecretsAll(spacename);
    }
}
