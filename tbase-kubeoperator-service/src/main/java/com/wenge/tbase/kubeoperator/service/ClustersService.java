package com.wenge.tbase.kubeoperator.service;

import com.wenge.tbase.commons.entity.RestResult;

public interface ClustersService {

	public RestResult<?> showAllClusters(String token);
	
	public RestResult<?> showACluster(String token,String name);
	
	//public RestResult<?> create(String token, String email, String name, String password, int isAdmin);
	
	public RestResult<?> deleteACluster(String token,String name);
}
