package com.loveplusplus.hellojni;

public class JNIInterface {

	static {
		// ����libAppConfig.so���ļ�
		// AppConfig����� Android Native Supportʱ���������
		// ���⣬ͨ���޸�Android.mk�е�LOCAL_MODULE�����޸��������
		System.loadLibrary("AppConfig");
	}

	public static native String getAppUrl();
}
