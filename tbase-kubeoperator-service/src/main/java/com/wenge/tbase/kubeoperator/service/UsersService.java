package com.wenge.tbase.kubeoperator.service;

import com.wenge.tbase.commons.entity.RestResult;

public interface UsersService {

	public RestResult<?> showAllUsers(String token);
	
	public RestResult<?> showAUser(String token,String name);
	
	public RestResult<?> create(String token, String email, String name, String password, int isAdmin);
	
	public RestResult<?> deleteAUser(String token,String name);
	
	public RestResult<?> updateAUser(String token, String email, String name, String password);
}
