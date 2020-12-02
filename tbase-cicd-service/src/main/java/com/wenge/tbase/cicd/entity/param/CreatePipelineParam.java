package com.wenge.tbase.cicd.entity.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Map;

/**
 * @ClassName: CreatePipelineParam
 * @Description: CreatePipelineParam
 * @Author: Wang XingPeng
 * @Date: 2020/12/2 15:34
 */
@ApiModel(description = "创建流水线参数类")
@Data
public class CreatePipelineParam {

    private Long pipelineId;


    private Map<String,String> globalVariable;


}
