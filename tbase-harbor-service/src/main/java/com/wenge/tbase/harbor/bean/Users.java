package com.wenge.tbase.harbor.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class Users implements Serializable {

    //用户ID
    private Integer user_id;
    //用户名称
    private String username;

    //邮箱
    private String email;

    //确认密码
    private String realname;

    //密码
    private String password;

    //密码版本
    private String password_version;

    //描述
    private String comment;

    //
    private Boolean admin_role_in_auth;

    //注册时间
    private Date creation_time;

    //更新时间
    private Date update_time;

    //删除状态
    private Boolean deleted;

    //角色ID
    private Integer role_id;

    //角色名称
    private String role_name;

    //重置uuid
    private String reset_uuid;

    //是否为管理员
    private Boolean sysadmin_flag;

    private String new_password;

    private String old_password;

    private Integer page;

    private Integer page_size;

}
