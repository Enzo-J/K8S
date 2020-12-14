package com.wenge.tbase.cicd.controller.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wenge.tbase.cicd.entity.CicdRepos;
import com.wenge.tbase.cicd.entity.param.CreateAndUpdateReposParam;
import com.wenge.tbase.cicd.entity.vo.GetReposListVo;
import com.wenge.tbase.cicd.service.CicdReposService;
import com.wenge.tbase.commons.result.ListVo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ReposControllerService
 * @Description: ReposControllerService
 * @Author: Wang XingPeng
 * @Date: 2020/12/9 16:55
 */
@Component
public class ReposControllerService {

    @Resource
    private CicdReposService reposService;

    /**
     * 获取代码仓库列表内容
     *
     * @param name
     * @param current
     * @param size
     * @return
     */
    public ListVo getReposList(String name, Integer current, Integer size) {
        QueryWrapper<CicdRepos> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",name);
        Page page = new Page(current, size);
        IPage list = reposService.page(page, queryWrapper);
        ListVo listVo = new ListVo();
        List<CicdRepos> records = list.getRecords();
        List<GetReposListVo> listVos = new ArrayList<>();
        records.stream().forEach(o -> {
            GetReposListVo vo = new GetReposListVo();
            BeanUtil.copyProperties(o, vo);
            listVos.add(vo);
        });
        listVo.setTotal(list.getTotal());
        listVo.setDataList(listVos);
        return listVo;
    }

    /**
     * 创建代码仓库内容
     *
     * @param param
     * @return
     */
    public Boolean createRepos(CreateAndUpdateReposParam param) {
        CicdRepos cicdRepos = new CicdRepos();
        cicdRepos.setName(param.getName());
        cicdRepos.setProjectName(param.getProjectName());
        cicdRepos.setCredentialId(param.getCredentialId());
        cicdRepos.setDescription(param.getDescription());
        return reposService.save(cicdRepos);
    }

    /**
     * 修改代码仓库内容
     *
     * @return
     */
    public Boolean updateRepos(CreateAndUpdateReposParam param) {
        CicdRepos cicdRepos = new CicdRepos();
        BeanUtil.copyProperties(param, cicdRepos);
        return reposService.updateById(cicdRepos);
    }


    /**
     * 删除代码仓库
     *
     * @param id
     * @return
     */
    public Boolean deleteRepos(Long id) {
        return reposService.removeById(id);
    }
}
