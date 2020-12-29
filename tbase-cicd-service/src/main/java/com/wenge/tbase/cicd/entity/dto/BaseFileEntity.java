package com.wenge.tbase.cicd.entity.dto;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
@ApiModel(value = "文件对象", description = "")
public class BaseFileEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "存储桶bucket")
	private String storeBucketKey;
	/**
	 * 文件名称
	 */
	@ApiModelProperty(value = "文件名(文件唯一key,支持路径，例如：/dir/1/pic.jpg)")
	private String fileName;
	
	/**
	 * 文件大小，单位byte
	 */
	@ApiModelProperty(value = "文件大小，单位byte")
	private long fileSize;
	/**
	 * 文件类型
	 */
	@ApiModelProperty(value = "文件类型")
	private String fileType;
	
	/**
	 * 文件访问地址
	 */
	@ApiModelProperty(value = "文件访问地址")
	private String url;
	/**
	 * 文件最后修改时间
	 */
	@ApiModelProperty(value = "文件最后修改时间")
	private Date lastModified;
	

}
