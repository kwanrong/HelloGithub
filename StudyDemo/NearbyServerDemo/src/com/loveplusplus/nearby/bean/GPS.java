package com.loveplusplus.nearby.bean;

import java.util.Date;

/** 
 * 用户gps信息
 * @author  程辉 
 * @version V1.0  创建时间：2013-6-15 下午4:01:22 
 */
public class GPS {

	private int id;
	private UserInfo user;
	private String longitude;
	private String latitude;
	private Date time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public UserInfo getUser() {
		return user;
	}
	public void setUser(UserInfo user) {
		this.user = user;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	
}
