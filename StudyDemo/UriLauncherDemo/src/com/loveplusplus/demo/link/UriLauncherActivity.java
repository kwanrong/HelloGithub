package com.loveplusplus.demo.link;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class UriLauncherActivity extends Activity {

	private static final String TAG = "UriLauncherActivity";
	private static final String HOST = "my.eoe.cn";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final Intent intent = getIntent();
		final Uri data = intent.getData();
		
		Log.d(TAG, "data:"+data.toString());
		Log.d(TAG, "data.gethost:"+data.getHost());
		
		if (HOST.equals(data.getHost())) {
			
			List<String> segments = data.getPathSegments();
	        if(segments!=null){
			Log.d(TAG,"segments:"+ segments.toString());
	        }
		}
	}

}
