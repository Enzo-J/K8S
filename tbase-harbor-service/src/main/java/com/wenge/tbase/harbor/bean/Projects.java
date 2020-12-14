package com.wenge.tbase.harbor.bean;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class Projects implements Serializable {

    //项目ID
    private Integer id;

    //项目名称
    private String name;
    private String project_name;

    //公私项目  公有1 私有0
    private String is_public;

    //分配空间大小  -1为不限制
    private Long storage_limit;

//    private JSONObject metadata;

    //页码
    private Integer page;
    //页大小
    private Integer page_size;

}
