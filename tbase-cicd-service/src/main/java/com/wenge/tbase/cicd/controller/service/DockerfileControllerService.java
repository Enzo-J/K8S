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
import com.wenge.tbase.cicd.entity.dto.BaseFileEntity;
import com.wenge.tbase.cicd.entity.param.CreateAndUpdateDockerfileParam;
import com.wenge.tbase.cicd.entity.vo.GetDockerfileListVo;
import com.wenge.tbase.cicd.feignService.FeignWOSService;
import com.wenge.tbase.cicd.service.CicdDockerfileService;
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

    @Resource
    private FeignWOSService wosService;

    private String bucketName = "szwg-bucket";

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
            wrapper.like("name", name);
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
        String fileName = "Dockerfile" + DateUtil.current(false);
        dockerfile.setFileName(fileName);
        dockerfile.setUrl(uploadDockerfile(fileName, param.getContent()));
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
        String fileName = "Dockerfile" + DateUtil.current(false);
        dockerfile.setFileName(fileName);
        dockerfile.setUrl(uploadDockerfile(fileName, param.getContent()));
        return dockerfileService.updateById(dockerfile);
    }

    /**
     * 上传DockerFile文件
     *
     * @param content
     * @return
     */
    public String uploadDockerfile(String fileName, String content) {
        String property = System.getProperty("user.dir");
        if ("/".equals(property)) {
            property = "";
        } else {
            property += "/tbase-cicd-service";
        }
        try {
            String path = property + "/src/main/resources/files/Dockerfile";
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
     * 删除dockerfile文件
     *
     * @param id
     * @return
     */
    public Boolean deleteDockerfile(Long id) {
        return dockerfileService.removeById(id);
    }

    /**
     * 根据name判断是否dockerfile存在
     *
     * @param name
     * @return
     */
    public Boolean judgeDockerfileExist(String name, Long id) {
        QueryWrapper<CicdDockerfile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        CicdDockerfile dockerfile = dockerfileService.getOne(queryWrapper);
        if (dockerfile != null) {
            if (id != null && dockerfile.getId() == id) {
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
