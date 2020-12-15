package com.wenge.tbase.nacos.service.impl;

import com.wenge.tbase.nacos.entity.MicroserviceRegistry;
import com.wenge.tbase.nacos.mapper.MicroserviceRegistryMapper;
import com.wenge.tbase.nacos.service.MicroserviceRegistryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Wang XingPeng
 * @since 2020-12-08
 */
@Service
public class MicroserviceRegistryServiceImpl extends ServiceImpl<MicroserviceRegistryMapper, MicroserviceRegistry> implements MicroserviceRegistryService {

}
