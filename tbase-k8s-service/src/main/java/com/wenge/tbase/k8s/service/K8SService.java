package com.wenge.tbase.k8s.service;

import java.util.List;
import java.util.Map;

public interface K8SService {


    /**
     * 获取全部命名空间
     */
    List getAllNameSpace();

    /**
     * 获取服务器节点信息
     */
    Map getNodeInfo();


}
