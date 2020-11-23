package com.wenge.tbase.cicd.service.impl;

import com.wenge.tbase.cicd.entity.Demo;
import com.wenge.tbase.cicd.mapper.DemoMapper;
import com.wenge.tbase.cicd.service.DemoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Wang XingPeng
 * @since 2020-11-18
 */
@Service
public class DemoServiceImpl extends ServiceImpl<DemoMapper, Demo> implements DemoService {

}
