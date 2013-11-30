package com.loveplusplus.file;

import java.io.IOException;

import sun.misc.BASE64Decoder;

import com.google.gson.Gson;

/** 
 * 类说明 
 * @author  程辉 
 * @version V1.0  创建时间：2013-1-25 上午11:27:27 
 */
public class FileUpload {

	public String upload(String json){
		Response res=new Response();
		int code=1;
		String msg="";
		Gson gson=new Gson();
		FileBean bean = gson.fromJson(json, FileBean.class);
		BASE64Decoder decoder = new BASE64Decoder();  
        byte[] imgBytes;
		try {
			imgBytes = decoder.decodeBuffer(bean.getFileContent());
			FileUtil.getFileFromBytes(imgBytes, "E:/test/"+bean.getFileName());
			msg="ok";
		} catch (IOException e) {
			e.printStackTrace();
			msg="error:"+e.getMessage();
		} 
		res.setCode(code);
		res.setMsg(msg);
		
		return gson.toJson(res);
	}
	
	
	
	
}
