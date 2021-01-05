package com.wenge.tbase.cicd.controller.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wenge.tbase.cicd.entity.CicdDockerfile;
import com.wenge.tbase.cicd.entity.CicdSonarqube;
import com.wenge.tbase.cicd.entity.dto.BaseFileEntity;
import com.wenge.tbase.cicd.entity.param.CreateAndUpdateDockerfileParam;
import com.wenge.tbase.cicd.entity.param.CreateAndUpdateSonarqubeParam;
import com.wenge.tbase.cicd.entity.vo.GetDockerfileListVo;
import com.wenge.tbase.cicd.entity.vo.GetSonarqubeListVo;
import com.wenge.tbase.cicd.feignService.FeignWOSService;
import com.wenge.tbase.cicd.service.CicdSonarqubeService;
import com.wenge.tbase.commons.result.ListVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Resource
    private FeignWOSService wosService;

    private String bucketName = "szwg-bucket";

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
        String fileName = "sonar-project" + DateUtil.current(false) + ".properties";
        sonarqube.setFileName(fileName);
        sonarqube.setUrl(uploadSonarFile(fileName, param.getContent()));
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
        String fileName = "sonar-project" + DateUtil.current(false) + ".properties";
        sonarqube.setFileName(fileName);
        sonarqube.setUrl(uploadSonarFile(fileName, param.getContent()));
        return sonarqubeService.updateById(sonarqube);
    }

    /**
     * 上传sonar文件
     *
     * @param content
     * @return
     */
    public String uploadSonarFile(String fileName, String content) {
        String property = System.getProperty("user.dir");
        if ("/".equals(property)) {
            property = "";
        } else {
            property += "/tbase-cicd-service";
        }
        try {
            String path = property + "/src/main/resources/files/sonar-project.properties";
            FileWriter writer = new FileWriter(path);
            writer.write(content);
            FileReader fileReader = new FileReader(path);
            BufferedInputStream fileReaderInputStream = fileReader.getInputStream();
            MultipartFile multipartFile = new MockMultipartFile("file", fileName, ContentType.APPLICATION_OCTET_STREAM.toString(), fileReaderInputStream);
            JSONObject upload = wosService.upload(multipartFile, bucketName);
            Map<String, Object> msg = JSONUtil.toBean(upload, Map.class);
            List<BaseFileEntity> objects = JSONUtil.toList(JSONUtil.parseArray(msg.get("msg")), BaseFileEntity.class);
            BaseFileEntity baseFileEntity = objects.get(0);
            return baseFileEntity.getUrl();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
    public Boolean judgeSonarqubeExist(String name, Long id) {
        QueryWrapper<CicdSonarqube> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        CicdSonarqube sonarqube = sonarqubeService.getOne(queryWrapper);
        if (sonarqube != null) {
            if (id != null && sonarqube.getId() == id) {
                return false;
            } else if (id == null) {
                return true;
            } else {
                return true;
            }
        }
        return false;
    }

}
