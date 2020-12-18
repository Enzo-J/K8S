package com.wenge.tbase.cicd.controller.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wenge.tbase.cicd.entity.CicdDockerfile;
import com.wenge.tbase.cicd.entity.CicdSonarqube;
import com.wenge.tbase.cicd.entity.param.CreateAndUpdateDockerfileParam;
import com.wenge.tbase.cicd.entity.param.CreateAndUpdateSonarqubeParam;
import com.wenge.tbase.cicd.entity.vo.GetDockerfileListVo;
import com.wenge.tbase.cicd.entity.vo.GetSonarqubeListVo;
import com.wenge.tbase.cicd.service.CicdSonarqubeService;
import com.wenge.tbase.commons.result.ListVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: SonarqubeControllerService
 * @Description: SonarqubeControllerService
 * @Author: Wang XingPeng
 * @Date: 2020/11/30 16:30
 */
@Component
@Slf4j
public class SonarqubeControllerService {
    @Resource
    private CicdSonarqubeService sonarqubeService;

    /**
     * 根据名称查询Sonarqube列表
     *
     * @param name
     * @param current
     * @param size
     * @return
     */
    public ListVo getSonarqubeList(String name, Integer current, Integer size) {
        QueryWrapper<CicdSonarqube> wrapper = new QueryWrapper();
        if (StringUtils.isNotEmpty(name)) {
            wrapper.like("name", name);
        }
        Page page = new Page(current, size);
        IPage list = sonarqubeService.page(page, wrapper);
        ListVo listVo = new ListVo();
        List<CicdSonarqube> records = list.getRecords();
        List<GetSonarqubeListVo> listVos = new ArrayList<>();
        records.stream().forEach(o -> {
            GetSonarqubeListVo vo = new GetSonarqubeListVo();
            BeanUtil.copyProperties(o, vo);
            listVos.add(vo);
        });
        listVo.setTotal(list.getTotal());
        listVo.setDataList(listVos);
        return listVo;
    }

    /**
     * 增加Sonarqube文件
     *
     * @param param
     * @return
     */
    public Boolean createSonarqube(CreateAndUpdateSonarqubeParam param) {
        CicdSonarqube sonarqube = new CicdSonarqube();
        sonarqube.setName(param.getName());
        sonarqube.setContent(param.getContent());
        sonarqube.setDescription(param.getDescription());
        return sonarqubeService.save(sonarqube);
    }


    /**
     * 修改Sonarqube文件
     *
     * @param param
     * @return
     */
    public Boolean updateSonarqube(CreateAndUpdateSonarqubeParam param) {
        CicdSonarqube sonarqube = new CicdSonarqube();
        BeanUtil.copyProperties(param, sonarqube);
        return sonarqubeService.updateById(sonarqube);
    }

    /**
     * 删除Sonarqube文件
     *
     * @param id
     * @return
     */
    public Boolean deleteSonarqube(Long id) {
        return sonarqubeService.removeById(id);
    }

    /**
     * 根据名称判断sonarqube是否存在
     *
     * @param name
     * @return
     */
    public Boolean judgeSonarqubeExist(String name) {
        QueryWrapper<CicdSonarqube> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        CicdSonarqube sonarqube = sonarqubeService.getOne(queryWrapper);
        if (sonarqube == null) {
            return false;
        } else {
            return true;
        }
    }

}
