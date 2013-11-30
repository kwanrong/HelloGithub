package com.loveplusplus.nearby.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loveplusplus.nearby.bean.GPS;
import com.loveplusplus.nearby.bean.UserInfo;
import com.loveplusplus.nearby.message.NearbyInfo;
import com.loveplusplus.nearby.message.NearbyInfoResponse;
import com.loveplusplus.nearby.service.Service;

/**
 * 类说明
 * 
 * @author 程辉
 * @version V1.0 创建时间：2013-6-15 下午3:33:20
 */
public class NearbyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		
		String longitude = req.getHeader("longitude");
		String latitude = req.getHeader("latitude");
		String userId = req.getHeader("user_id");
		

		Service service = new Service();

		UserInfo user=new UserInfo();
		user.setId(userId);
		
		
		 // 保存或更新用户gps数据
		 GPS gps = new GPS();
		 gps.setLatitude(latitude);
		 gps.setLongitude(longitude);
		 gps.setUser(user);
		 gps.setTime(new Date());
		 try {
			service.save(gps);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 查找附近的人
		 NearbyInfoResponse result = new NearbyInfoResponse();
		
		try {
			List<NearbyInfo> list = service.findList(userId, longitude, latitude);
			result.setList(list);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		

		setSuccess(resp, result);
	}

	protected String getStringParameter(HttpServletRequest req,
			String parameter, String defaultValue) {
		String value = req.getParameter(parameter);
		if (value == null || value.trim().isEmpty()) {
			value = defaultValue;
		}
		return value.trim();
	}

	protected void setSuccess(HttpServletResponse resp, Object obj)
			throws IOException {

		GsonBuilder builder = new GsonBuilder();
		// 不转换没有 @Expose 注解的字段
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();

		String json = gson.toJson(obj);

		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.print(json);
		out.flush();
		out.close();

	}

}
