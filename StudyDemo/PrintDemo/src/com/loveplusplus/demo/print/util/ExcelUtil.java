package com.loveplusplus.demo.print.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import android.content.Context;
import android.util.Log;

public class ExcelUtil {

	private static final String TAG = "ExcelUtil";

	public static void excel(Context context, File distFile,
			String templateName, String pref, Map<String, String> map) {
		try {
			InputStream tempInputStream = context.getAssets().open(templateName);
			// 获得一个excel实例
			HSSFWorkbook workbook = new HSSFWorkbook(tempInputStream);
			// 取得excel文件的第一个工作表
			HSSFSheet sheet = workbook.getSheetAt(0);
			// 通过迭代器遍历获得行数据后遍历行数据获得单元格数据，并保存在列表sheetData中
			Iterator rows = sheet.rowIterator();
			while (rows.hasNext()) {
				HSSFRow row = (HSSFRow) rows.next();
				Iterator cells = row.cellIterator();
				while (cells.hasNext()) {
					HSSFCell cell = (HSSFCell) cells.next();

					String key = cell.getRichStringCellValue().getString();
					if (key.startsWith(pref) && map.containsKey(key)) {
						cell.setCellValue(map.get(key));
					}
				}
			}

			workbook.write(new FileOutputStream(distFile));

		} catch (Exception e) {
			Log.d(TAG, "", e);
		}
	}
	public static boolean excel2(String distFile,Context context,String pref, Map<String, String> map) {
		try {
			InputStream tempInputStream = context.getAssets().open("demo.xls");
			// 获得一个excel实例
			HSSFWorkbook workbook = new HSSFWorkbook(tempInputStream);
			// 取得excel文件的第一个工作表
			HSSFSheet sheet = workbook.getSheetAt(0);
			// 通过迭代器遍历获得行数据后遍历行数据获得单元格数据，并保存在列表sheetData中
			Iterator rows = sheet.rowIterator();
			while (rows.hasNext()) {
				HSSFRow row = (HSSFRow) rows.next();
				Iterator cells = row.cellIterator();
				while (cells.hasNext()) {
					HSSFCell cell = (HSSFCell) cells.next();
					
					String key = cell.getRichStringCellValue().getString();
					if (key.startsWith(pref) && map.containsKey(key)) {
						cell.setCellValue(map.get(key));
					}
				}
			}
			//File distFile=FileUtils.getFile(context,"hello","xls","excel");
			FileOutputStream fos=new FileOutputStream(distFile);
			workbook.write(fos);
			fos.flush();
			fos.close();
			return true;
		} catch (Exception e) {
			Log.d(TAG, "", e);
		}
		return false;
	}

}
