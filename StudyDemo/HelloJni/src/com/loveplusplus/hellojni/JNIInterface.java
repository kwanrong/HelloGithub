package com.loveplusplus.hellojni;

public class JNIInterface {

	static {
		// 加载libAppConfig.so库文件
		// AppConfig是添加 Android Native Support时输入的名称
		// 另外，通过修改Android.mk中的LOCAL_MODULE可以修改这个名称
		System.loadLibrary("AppConfig");
	}

	public static native String getAppUrl();
}
