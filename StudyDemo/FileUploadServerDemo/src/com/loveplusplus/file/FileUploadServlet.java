package com.loveplusplus.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Decoder;

import com.google.gson.Gson;

/**
 * 类说明
 * 
 * @author 程辉
 * @version V1.0 创建时间：2013-1-28 下午2:24:22
 */
public class FileUploadServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
		} catch (Exception e) { 
			e.printStackTrace();
		}

		Response res = new Response();
		int code = 1;
		String msg = "";

		Gson gson = new Gson();
		FileBean bean = gson.fromJson(jb.toString(), FileBean.class);
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] imgBytes;
		try {
			imgBytes = decoder.decodeBuffer(bean.getFileContent());
			FileUtil.getFileFromBytes(imgBytes, "E:/test/" + bean.getFileName());
			msg = "ok";
		} catch (IOException e) {
			e.printStackTrace();
			msg = "error:" + e.getMessage();
			
		}
		res.setCode(code);
		res.setMsg(msg);
		String json = gson.toJson(res);
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}

}
