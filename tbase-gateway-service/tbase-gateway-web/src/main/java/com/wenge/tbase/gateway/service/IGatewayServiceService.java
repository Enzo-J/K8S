package com.wenge.tbase.gateway.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wenge.tbase.gateway.entity.po.GatewayService;
import com.wenge.tbase.gateway.entity.vo.GatewayServiceVo;
import com.wenge.tbase.gateway.entity.vo.GatewayServiceVoPage;
import com.wenge.tbase.gateway.entity.vo.SynchronizServiceVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dangwei
 * @since 2020-12-17
 */
public interface IGatewayServiceService extends IService<GatewayService> {	
	
	/**
	 * 根据服务名称
	 * @param serviceName 服务名称
	 * @param current 当前分页序号
	 * @param size 分页显示的条数
	 * @return GatewayServiceVoPage
	 */
	public GatewayServiceVoPage query(String serviceName,long current,long size);
	
	
	public  List<GatewayServiceVo> getAll(); 
	
	/**
	 * 同步注册中心服务至本地
	 * @return SynchronizServiceVo
	 */
	public SynchronizServiceVo synchronizServices(); 
	
	public boolean submit2Db(List<GatewayService> intersection,List<GatewayService> reduceServer,List<GatewayService> offlineServer);

}
