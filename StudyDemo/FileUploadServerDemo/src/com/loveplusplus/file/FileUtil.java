package com.loveplusplus.file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/** 
 * 类说明 
 * @author  程辉 
 * @version V1.0  创建时间：2013-1-28 下午2:30:07 
 */
public class FileUtil {

	public static File getFileFromBytes(byte[] b, String outputFile) {
		BufferedOutputStream stream = null;
		File file = null;
		try {
			file = new File(outputFile);
			FileOutputStream fstream = new FileOutputStream(file);
			stream = new BufferedOutputStream(fstream);
			stream.write(b);
		} catch (Exception e) {
			e.printStackTrace();
			//log.error("字节转file时出现异常", e);
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					//log.error("字节转file关闭流时出现异常", e1);
				}
			}
		}
		return file;
	}
	
}
