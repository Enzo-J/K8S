package com.wenge.tbase.kubeoperator.service;

import com.wenge.tbase.commons.entity.RestResult;

public interface HostsService {

	public RestResult<?> showAllHosts(String token);
	
	public RestResult<?> showAHosts(String token,String name);
	
	public RestResult<?> create(String token, String credentialId, String ip, String name, int port);
	
	public RestResult<?> deleteAHosts(String token,String name);
}
