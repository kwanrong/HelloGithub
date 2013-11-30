package com.loveplusplus.demo.print.util;

import java.io.File;
import java.io.IOException;

import android.content.Context;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;

/**
 * 字体工具类
 * 
 * @author Administrator
 * 
 */
public class FontUtil {

	/**
	 * 把assets目录下的字体文件加载到sdcard中,并且返回该字体 根据提供的字体名字加载改字体，该方法只适合itext生成pdf时调用
	 * 
	 * @param context
	 * @param fontSize
	 *            字体大小
	 * @param ttfName
	 *            字体名字
	 * @return Font字体对象
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static Font getFont(Context context, int fontSize, String ttfName)
			throws DocumentException, IOException {
		return getFont(context, fontSize, ttfName, Font.NORMAL, null);
	}

	public static Font getFont(Context context, int fontSize, String ttfName,
			int style, BaseColor color) throws DocumentException, IOException {
		

		BaseFont baseFont =getBaseFont(context, ttfName);

		if (null == color) {
			return new Font(baseFont, fontSize, style);
		} else {
			return new Font(baseFont, fontSize, style, color);
		}
	}
	
	public static BaseFont getBaseFont(Context context,  String ttfName) throws DocumentException, IOException {
		
		String fontPath = FileUtils.getFilesDir(context);
		
		File file1 = new File(fontPath, ttfName);
		
		if (!file1.exists()) {
			FileUtils.copyAssetFileToSDCard(context, ttfName, fontPath);
		}
		
		BaseFont baseFont = BaseFont.createFont(file1.getAbsolutePath(),
				BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		
		return baseFont;
	}

	
}
