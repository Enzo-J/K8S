package com.wenge.tbase.harbor.service;

import com.wenge.tbase.harbor.bean.Users;
import com.wenge.tbase.harbor.result.RestResult;

import java.util.HashMap;

public interface HarborUsersService {

	RestResult<?> addUsersService(Users users);

	RestResult<?> updateUsersService(Users users);

	RestResult<?> deleteUsersService(Users users);

	RestResult<?> resetPasswordService(Users users);

	RestResult<?> getUsersListService(Users users);

	RestResult<?> getUsersByIdService(Users users);

}
