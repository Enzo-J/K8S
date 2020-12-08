package com.wenge.tbase.harbor.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author dell
 */
@Setter
@Getter
public class LoginUserVo implements Serializable {

    private String principal;

    private String password;

}
