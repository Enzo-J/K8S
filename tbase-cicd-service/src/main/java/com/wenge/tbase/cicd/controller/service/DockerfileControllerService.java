package com.wenge.tbase.cicd.controller.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wenge.tbase.cicd.entity.CicdDockerfile;
import com.wenge.tbase.cicd.entity.param.CreateAndUpdateDockerfileParam;
import com.wenge.tbase.cicd.entity.vo.GetDockerfileListVo;
import com.wenge.tbase.cicd.service.CicdDockerfileService;
import com.wenge.tbase.commons.result.ListVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: DockerfileControllerService
 * @Description: DockerfileControllerService
 * @Author: Wang XingPeng
 * @Date: 2020/11/30 10:48
 */
@Component
@Slf4j
public class DockerfileControllerService {

    @Resource
    private CicdDockerfileService dockerfileService;

    /**
     * 根据名称查询dockerfile列表
     *
     * @param name
     * @param current
     * @param size
     * @return
     */
    public ListVo getDockerfileList(String name, Integer current, Integer size) {
        QueryWrapper<CicdDockerfile> wrapper = new QueryWrapper();
        if (StringUtils.isNotEmpty(name)) {
            wrapper.eq("name", name);
        }
        Page page = new Page(current, size);
        IPage list = dockerfileService.page(page, wrapper);
        ListVo listVo = new ListVo();
        List<CicdDockerfile> records = list.getRecords();
        List<GetDockerfileListVo> listVos = new ArrayList<>();
        records.stream().forEach(o -> {
            GetDockerfileListVo vo = new GetDockerfileListVo();
            BeanUtil.copyProperties(o, vo);
            listVos.add(vo);
        });
        listVo.setTotal(list.getTotal());
        listVo.setDataList(listVos);
        return listVo;
    }

    /**
     * 增加dockerfile文件
     *
     * @param param
     * @return
     */
    public Boolean createDockerfile(CreateAndUpdateDockerfileParam param) {
        CicdDockerfile dockerfile = new CicdDockerfile();
        dockerfile.setName(param.getName());
        dockerfile.setContent(param.getContent());
        return dockerfileService.save(dockerfile);
    }


    /**
     * 修改dockerfile文件
     *
     * @param param
     * @return
     */
    public Boolean updateDockerfile(CreateAndUpdateDockerfileParam param) {
        CicdDockerfile dockerfile = new CicdDockerfile();
        BeanUtil.copyProperties(param, dockerfile);
        return dockerfileService.updateById(dockerfile);
    }

    /**
     * 删除dockerfile文件
     *
     * @param id
     * @return
     */
    public Boolean deleteDockerfile(Long id) {
        return dockerfileService.removeById(id);
    }
}
