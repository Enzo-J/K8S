package com.wenge.tbase.harbor.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author: chf
 * @ClassName: Repositories  镜像实体
 * @Description: todo
 * @Date: 2020/11/27
 */
@Setter
@Getter
public class Repositories implements Serializable {

    //镜像ID
    private Integer id;

    //镜像名称
    private String name;

    //所属项目ID
    private Integer prject_id;

    //所属项目名称
    private String project_name;

    //命令数？
    private Integer artifact_count;

    //下载次数
    private Integer pull_count;

    //关键字检索
    private String q;
    //页码
    private Integer page;
    //页大小
    private Integer page_size;
}
