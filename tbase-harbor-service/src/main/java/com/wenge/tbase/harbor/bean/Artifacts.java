package com.wenge.tbase.harbor.bean;

import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Artifacts implements Serializable {

    private Integer id;

    private Integer name;

    private Boolean with_tag;
    private Boolean with_scan_overview;
    private Boolean with_label;

    private Integer project_id;
    private Integer repository_id;
    //镜像名称
    private String repository_name;
    private Long size; //单位KB
    //页码
    private Integer page;
    //页大小
    private Integer page_size;

}
