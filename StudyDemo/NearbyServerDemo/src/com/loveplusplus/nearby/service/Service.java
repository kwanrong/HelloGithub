package com.loveplusplus.nearby.service;

import java.sql.SQLException;
import java.util.List;

import com.loveplusplus.nearby.bean.GPS;
import com.loveplusplus.nearby.bean.UserInfo;
import com.loveplusplus.nearby.dao.Dao;
import com.loveplusplus.nearby.message.NearbyInfo;
import com.loveplusplus.nearby.util.GPSUtil;

/** 
 * 类说明 
 * @author  程辉 
 * @version V1.0  创建时间：2013-6-15 下午4:08:52 
 */
public class Service {

	public void save(GPS gps) throws SQLException {
		Dao dao=new Dao();
		dao.save(gps);
	}

	public List<NearbyInfo> findList(String userId, String longitude,
			String latitude) throws SQLException {
		Dao dao=new Dao();
		
		List<NearbyInfo> list = dao.findGPSList();
		
		for(NearbyInfo info:list){
			Double d = GPSUtil.computeDistance(Double.valueOf(latitude), Double.valueOf(longitude), Double.valueOf(info.getLatitude()), Double.valueOf(info.getLongitude()));
			
			info.setDistance(d.intValue()+"米");
		}
		
		return list;
	}

	public void saveOrUpdateUser(UserInfo user) throws SQLException {
		Dao dao=new Dao();
		
		UserInfo u=dao.findUser(user.getId());
		
		if(null==u){
			dao.saveUser(user);
		}else{
			dao.updateUser(user);
		}
	}

	
}
