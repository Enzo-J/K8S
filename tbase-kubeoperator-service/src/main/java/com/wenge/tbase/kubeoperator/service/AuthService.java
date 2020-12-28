package com.wenge.tbase.kubeoperator.service;

import com.wenge.tbase.commons.entity.RestResult;

public interface AuthService {

	public RestResult<?> getToken(String username, String password);
}
