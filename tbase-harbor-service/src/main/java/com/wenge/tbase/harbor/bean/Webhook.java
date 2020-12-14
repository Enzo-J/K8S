package com.wenge.tbase.harbor.bean;

import cn.hutool.json.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class Webhook implements Serializable {

    //webhook ID
    private Integer id;

    private String description;

    private String[] event_types;

    private Boolean enabled;

    private String name;

    private JSONObject[] targets;

    private Date creation_time;

    private String creator;


}
