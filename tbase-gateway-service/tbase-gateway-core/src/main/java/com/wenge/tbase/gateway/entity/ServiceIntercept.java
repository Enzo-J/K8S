package com.wenge.tbase.gateway.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ServiceIntercept implements Serializable {
    
	private static final long serialVersionUID = -1706392633709913448L;

	private Integer id;

    private String url;

    private String appId;

    private String appName;

    private Integer matchingRules;

    private String createBy;

    private String modifyBy;

    private Date createTime;

    private  Date modifyTime;

}
