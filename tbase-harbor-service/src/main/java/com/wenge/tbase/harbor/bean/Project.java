package com.wenge.tbase.harbor.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Author: chf
 * @ClassName: Project
 * @Description: todo
 * @Date: 2020/11/27
 */

@Setter
@Getter
public class Project {

    private int project_id;
    private int owner_id;
    private String name;
    private Date creation_time;
    private Date update_time;
    private boolean deleted;
    private String owner_name;
    private int current_user_role_id;
    private String current_user_role_ids;
    private int repo_count;
    private int chart_count;
    private Metadata metadata;


}
