package com.wenge.tbase.harbor.bean;

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

    //公私项目  公有1 私有0
    private String is_public;

    //页码
    private Integer page;
    //页大小
    private Integer page_size;

}
