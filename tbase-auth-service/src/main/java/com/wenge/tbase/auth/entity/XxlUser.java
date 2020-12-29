package com.wenge.tbase.auth.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author DE
 * @Auther: 蔡武
 * @Date: 2020/10/27 14:14
 * @Description:
 */
@Data
@ToString
public class XxlUser {

    private static final Long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;


    /**
     * 姓名
     */
    private String name;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 证件号
     */
    private String idCard;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户类型（1：个人用户，2：法人用户，3：政务网用户）
     */
    private String userType;

    /**
     * 所属部门名称
     */
    private String departMentName;

    /**
     * 用户登录时间
     */
    private Date loginDate;

    /**
     * 登录企业ID
     */
    private Integer enterpriseId;

    /**
     * 登录企业名称
     */
    private String enterpriseName;

    /**
     * 统一社会信用代码
     */
    private String socialCreditCode;

    /**
     * 企业类型（1，龙华，2深圳市内，3广东省内，4国内）
     */
    private String registerType;

    /**
     * 用户源
     */
    private String userSource;

    /**
     * 是否管理员
     */
    private boolean ifManage = false;

}
