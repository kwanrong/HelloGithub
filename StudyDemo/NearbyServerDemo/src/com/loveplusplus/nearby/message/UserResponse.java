package com.loveplusplus.nearby.message;

import com.google.gson.annotations.Expose;

/** 
 * 类说明 
 * @author  程辉 
 * @version V1.0  创建时间：2013-6-17 上午11:55:40 
 */
public class UserResponse {

	@Expose
	private String msg;
	@Expose
	private String code;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
