package com.wenge.tbase.gateway.service.impl;



import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wenge.tbase.commons.entity.ErrorType;
import com.wenge.tbase.commons.entity.RestResult;
import com.wenge.tbase.gateway.dao.GatewayServiceMapper;
import com.wenge.tbase.gateway.entity.po.GatewayService;
import com.wenge.tbase.gateway.entity.vo.GatewayServiceVo;
import com.wenge.tbase.gateway.entity.vo.GatewayServiceVoPage;
import com.wenge.tbase.gateway.entity.vo.SynchronizServiceVo;
import com.wenge.tbase.gateway.exception.RpcException;
import com.wenge.tbase.gateway.exception.WengeException;
import com.wenge.tbase.gateway.fegin.MicroserviceConfigFeign;
import com.wenge.tbase.gateway.service.IGatewayServiceService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dangwei
 * @since 2020-12-17
 */
@Service
public class GatewayServiceServiceImpl extends ServiceImpl<GatewayServiceMapper, GatewayService> implements IGatewayServiceService {
    private static final String STATUS_Y="Y";
    private static final String PREFIX_URI="lb://";
    private static final String SERVER_TYPE="http";
    private static final String API_PATH="/swagger-ui.html";
	@Autowired
	private GatewayServiceMapper gatewayServiceMapper;
	
	@Autowired
	private MicroserviceConfigFeign microserviceConfigFeign;
	
	@Override
	public GatewayServiceVoPage query(String serviceName,long current,long size) {	
		QueryWrapper<GatewayService> queryWrapper=new QueryWrapper<GatewayService>();
		queryWrapper.like(StringUtils.isNotBlank(serviceName),"service_name", serviceName);	
		queryWrapper.orderByDesc("created_time");
		IPage<GatewayService> result=gatewayServiceMapper.selectPage(new Page(current, size), queryWrapper);		
		return new GatewayServiceVoPage(result);
	}

	@Override
//	@Transactional
	public SynchronizServiceVo synchronizServices() {
		RestResult<?> result = microserviceConfigFeign.getServiceList(true, false, 1, 10000000, null,null,null);
		if(200 != result.status) 
			throw new RpcException(result.data.toString(),result.status);		
		//交集
		List<GatewayService> intersection = null;
	    //fromCenter - 交集 =  新增集合
		List<GatewayService> reduceServer = null;	
		//下线集合
		List<GatewayService> offlineServer = null;	
				
		try {
			//注册中心同步的服务对象
			List<GatewayService> gatewayServicefromCenter=new LinkedList<>();
			//本地服务对象集合
			List<GatewayService> gatewayServicefromLocal=new LinkedList<>();
			
			gatewayServicefromLocal=gatewayServiceMapper.selectList(new QueryWrapper<GatewayService>());			
			JSONObject data =JSONObject.parseObject(JSON.toJSONString(result.data));			
			List<JSONObject> microserviceList=data.getJSONArray("serviceList").toJavaList(JSONObject.class);					
//			boolean flag=false;			
			if(!CollectionUtils.isEmpty(microserviceList)) {				
				for (JSONObject service : microserviceList) {			
					gatewayServicefromCenter.add(conver2GatewayService(service));	
				}			
			}
			
			List<GatewayService> gatewayServicefromLocalTemp=gatewayServicefromLocal;
//			//更新交集
			intersection =gatewayServicefromCenter.stream().filter(item -> find1(item.getServiceName(), gatewayServicefromLocalTemp) > -1).collect(Collectors.toList());
//			
//			for (GatewayService gatewayService : intersection) {
//				flag=this.update(gatewayService, new LambdaQueryWrapper<GatewayService>().eq(GatewayService::getServiceName, gatewayService.getServiceName()));
//			}
//			
//			//插入差集
			reduceServer =gatewayServicefromCenter.stream().filter(item -> !(find1(item.getServiceName(), gatewayServicefromLocalTemp) > -1)).collect(Collectors.toList());
//			flag=this.saveBatch(reduceServer);
//						
//			//更新已经下线的服务			
			offlineServer =gatewayServicefromLocalTemp.stream().filter(item -> !(find1(item.getServiceName(), gatewayServicefromCenter) > -1)).collect(Collectors.toList());
//			for (GatewayService gatewayService : reduceServer1) {
//				gatewayService.setStatus("N");
//				flag=this.update(gatewayService, new LambdaQueryWrapper<GatewayService>().eq(GatewayService::getServiceName, gatewayService.getServiceName()));
//			}
//			if(!flag)
//				throw new WengeException(ErrorType.DB_FAILED);		
//			//覆盖服务名集合
//			intersectionStr=intersection.stream().map(e -> e.getServiceName()).collect(Collectors.toList());
//			//新增服务名集合
//			reduceStr=reduceServer.stream().map(e -> e.getServiceName()).collect(Collectors.toList());
						
		} catch (Exception e) {
			e.printStackTrace();
			throw new WengeException();			
		}
		
		return new SynchronizServiceVo(reduceServer,intersection,offlineServer,reduceServer.size(),intersection.size(),offlineServer.size());
	}		
	
	@Override
	@Transactional
	public boolean submit2Db(List<GatewayService> intersection,List<GatewayService> reduceServer,List<GatewayService> offlineServer) {
		boolean flag=false;
	    try {
			//批量更新交集
			if(!CollectionUtils.isEmpty(intersection)) {			
				for (GatewayService gatewayService : intersection) {
					flag=this.update(gatewayService, new LambdaQueryWrapper<GatewayService>().eq(GatewayService::getServiceName, gatewayService.getServiceName()));
				}	
			}		
			//插入差集
			if(!CollectionUtils.isEmpty(intersection)) {
				flag=this.saveBatch(reduceServer);
			}
						
			//更新已经下线的服务
			if(!CollectionUtils.isEmpty(offlineServer))
			for (GatewayService gatewayService : offlineServer) {
				gatewayService.setStatus("N");
				flag=this.update(gatewayService, new LambdaQueryWrapper<GatewayService>().eq(GatewayService::getServiceName, gatewayService.getServiceName()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;			
			throw new WengeException(ErrorType.DB_FAILED);		
		}	
		return flag;
	}

	@Override
	public List<GatewayServiceVo> getAll() {
		List<GatewayService> list= gatewayServiceMapper.selectList(new LambdaQueryWrapper<GatewayService>().eq(GatewayService::getStatus, "Y"));
		List<GatewayServiceVo> result1=list.stream().map(GatewayServiceVo::new).collect(Collectors.toList());
//		list.forEach(item ->{
//			GatewayServiceVo gatewayServiceVo = new GatewayServiceVo(item);
//			result.add(gatewayServiceVo);
//		});
		return result1;
	}	
	
	private  GatewayService conver2GatewayService(JSONObject service) {
		GatewayService gatewayServiceEntity=new GatewayService();		
		String servName=service.getString("name");
		gatewayServiceEntity.setServiceName(servName);
		gatewayServiceEntity.setServiceUri(PREFIX_URI+servName);
		gatewayServiceEntity.setStatus(STATUS_Y);
		gatewayServiceEntity.setSynchronize(STATUS_Y);
		gatewayServiceEntity.setServerType(SERVER_TYPE)	;
		//获取服务实例对象
		RestResult<?> result = microserviceConfigFeign.getInstanceList(servName, null, null, null, true);
		if(200 != result.status) 
			throw new RpcException(result.data.toString(),result.status);		
		JSONObject data =JSON.parseObject(JSON.toJSONString(result.data));	
		List<JSONObject> microserviceList=data.getJSONArray("hosts").toJavaList(JSONObject.class);
		//默认取第一个服务实例的ip+port
		if(!CollectionUtils.isEmpty(microserviceList)) {
			String ip=microserviceList.get(0).getString("ip");
			Integer  port = microserviceList.get(0).getInteger("port");
			gatewayServiceEntity.setServiceInstanceApiUrl(ip+":"+port+API_PATH);
		}		
		return gatewayServiceEntity;		
	}
	
	public static int find1(String serverNmae,List<GatewayService> list) {
        int res = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getServiceName().equals(serverNmae)) {
                res = i;
                break;
            }
        }
        return res;
    }
	
	
	
//	public static void main(String[] args) {
//		GatewayService s=new GatewayService();
//		s.setServiceName("1");
//		GatewayService s1=new GatewayService();
//		s1.setServiceName("2");
//		
//		GatewayService s10=new GatewayService();
//		s10.setServiceName("3");
//		
//		List<GatewayService> l1=new LinkedList<GatewayService>();
//		l1.add(s);
//		l1.add(s1);
////		l1.add(s10);
//		
//		GatewayService s2=new GatewayService();	
//		s2.setServiceName("1");
//		GatewayService s3=new GatewayService();
//		s3.setServiceName("2");
//		
//		
//		List<GatewayService> l2=new LinkedList<GatewayService>();
//		l2.add(s2);
//		l2.add(s3);
//		l2.add(s10);
//		List<GatewayService> intersectList = l1.stream()
//                .filter(item -> !(find1(item.getServiceName(), l2) > -1)).collect(Collectors.toList());
//		intersectList.forEach(i ->{
//			System.out.println(i.getServiceName());
//		});
//	}
//	
}
