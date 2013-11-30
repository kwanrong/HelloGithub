#include <jni.h>
extern "C"
jstring
Java_com_loveplusplus_hellojni_JNIInterface_getAppUrl(JNIEnv* env,jobject thiz) {
	//return (*env)->NewStringUTF(env,"http://www.baidu.com"); //c
	return env->NewStringUTF("http://www.baidu.com");
}
