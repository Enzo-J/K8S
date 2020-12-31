package com.wenge.tbase.gateway.entity.base;

import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import lombok.Data;

/**
 * Created by dangwei
 */
@Data
public class BaseQueryParam<T extends Object> {
    private Date createdTimeStart;
    private Date createdTimeEnd;

    public QueryWrapper<T> build() {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge(null != this.createdTimeStart, "created_time", this.createdTimeStart)
                .le(null != this.createdTimeEnd, "created_time", this.createdTimeEnd);
        return queryWrapper;
    }
}
