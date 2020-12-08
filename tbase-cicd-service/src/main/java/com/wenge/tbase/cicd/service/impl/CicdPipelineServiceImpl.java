package com.wenge.tbase.cicd.service.impl;

import com.wenge.tbase.cicd.entity.CicdPipeline;
import com.wenge.tbase.cicd.mapper.CicdPipelineMapper;
import com.wenge.tbase.cicd.service.CicdPipelineService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Wang XingPeng
 * @since 2020-11-19
 */
@Service
public class CicdPipelineServiceImpl extends ServiceImpl<CicdPipelineMapper, CicdPipeline> implements CicdPipelineService {

    @Resource
    private CicdPipelineMapper cicdPipelineMapper;

    @Override
    public Long insertPipeline(CicdPipeline cicdPipeline) {
        return cicdPipelineMapper.insertPipeline(cicdPipeline);
    }
}
