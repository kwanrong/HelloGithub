package com.loveplusplus.demo.print;

import java.io.File;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;

import com.loveplusplus.demo.print.util.AppUtil;
import com.loveplusplus.demo.print.util.FileUtils;
import com.loveplusplus.demo.print.util.PrinterUtil;

public class PrintService extends IntentService {

	public PrintService() {
		super("PrintService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// 判断插件是否安装
		boolean appIsInstalled = AppUtil.appIsInstalled(this,
				PrinterUtil.packageName);

		if (!appIsInstalled) {
			Intent installIntent = new Intent(Intent.ACTION_VIEW);
			String dirPath = FileUtils.getCacheDir(this);
			File file = FileUtils.copyAssetFileToSDCard(this, "printer.apk",
					dirPath);
			installIntent.setDataAndType(Uri.fromFile(file),
					"application/vnd.android.package-archive");
			installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(installIntent);
		} else {
			Uri uri = intent.getData();
			Intent intent3 = PrinterUtil.getPrintIntent(uri, PrinterUtil.PDF1);
			startActivity(intent3);
		}

	}

}
