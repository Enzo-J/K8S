package com.wenge.tbase.harbor.service;

import com.wenge.tbase.harbor.bean.HarborProject;
import com.wenge.tbase.harbor.bean.HarborUser;
import com.wenge.tbase.harbor.bean.ImagesTags;
import com.wenge.tbase.harbor.bean.Repositories;

import java.util.List;

/**
 * @Author: chf
 * @ClassName: HarborRequest
 * @Description: todo
 * @Date: 2020/11/27
 */
public interface HarborRequest {
    /**
     * 创建Harbor用户
     * @param harborUser
     * @return
     */
    void createUser(HarborUser harborUser);

    /**
     * 创建project
     * @param harborProject
     * @return
     */
    void createProject(HarborProject harborProject, String username, String password);

    /**
     * 通过项目名称获取项目信息
     * @return
     */
    Integer queryProject(String name);

    /**
     * 通过项目id获取所有镜像
     * @return
     */
    List<Repositories> queryImagesByProjectId(Integer projectId);

    /**
     * 通过镜像名获取所有镜像标签
     * @return
     */
    List<ImagesTags> queryImagesTagsByImageName(String imageName);
}
