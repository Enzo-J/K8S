package com.wenge.tbase.kubeoperator.service;

import com.wenge.tbase.commons.entity.RestResult;

public interface ProjectsService {

	public RestResult<?> showAllProjects(String token);
	
	public RestResult<?> showAProject(String token,String name);
	
	public RestResult<?> create(String token, String description, String name);
	
	public RestResult<?> deleteAProject(String token,String name);
	
	public RestResult<?> updateAProject(String token, String description, String name);//需要id
}
