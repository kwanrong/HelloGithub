package com.loveplusplus.demo.print.util;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

public class AppUtil {

	/**
	 * 获取app的版本号
	 * 
	 * @param context
	 * @return
	 */
	public static int getVersionCode(Context context) {
		int v = -1;
		try {
			v = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			// 不用理会
		}
		return v;
	}

	/**
	 * 
	 * 判断app 是否安装 com.dynamixsoftware.printershare
	 * 
	 * @param context
	 * @param pageName
	 * @return
	 */
	public static boolean appIsInstalled(Context context, String pageName) {
		try {
			context.getPackageManager().getPackageInfo(pageName, 0);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
	}

}
