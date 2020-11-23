package com.wenge.tbase.commons.result;

import lombok.Data;

import java.util.List;

/**
 * @ClassName: ListVo
 * @Description: total:总数 dataList:数据list
 * @Author: Wang XingPeng
 * @Date: 2020/8/24 14:09
 */
@Data
public class ListVo<T>{

    private Long total;

    private List<T> dataList;
}
