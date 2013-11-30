package com.loveplusplus.demo.print.util;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;

public class PrinterUtil {

	
	public static final String packageName = "com.dynamixsoftware.printershare";
	private static final String pdfActivityName = "com.dynamixsoftware.printershare.ActivityPrintPDF";
	private static final String excelActivityName = "com.dynamixsoftware.printershare.ActivityPrintDocuments";
	
	public static final String packageName2 = "com.dynamixsoftware.printhand.premium";
	private static final String pdfActivityName2 = "com.dynamixsoftware.printhand.ui.ActivityPreviewFiles";
	
	public static final int PDF1 = 1;
	public static final int PDF2 = 2;
	public static final int WORD = 3;
	public static final int EXCEL = 4;

	private static Intent sendPDFToPrintShare(Uri uri) {
		Intent intent = new Intent();
		intent.setComponent(new ComponentName(PrinterUtil.packageName,
				PrinterUtil.pdfActivityName));
		intent.setAction("android.intent.action.VIEW");
		intent.setType("application/pdf");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setData(uri);
		return intent;

	}
	private static Intent sendPDFToPrinthand(Uri uri) {
		Intent intent = new Intent();
		intent.setComponent(new ComponentName(PrinterUtil.packageName2,
				PrinterUtil.pdfActivityName2));
		
		intent.setAction("android.intent.action.VIEW");
		intent.setType("application/pdf");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setData(uri);
		return intent;
		
	}

	private static Intent sendExcel(Uri uri) {
		Intent intent = new Intent();
		intent.setComponent(new ComponentName(PrinterUtil.packageName,
				PrinterUtil.excelActivityName));
		intent.setAction("android.intent.action.VIEW");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setType("application/vnd.ms-excel");
		intent.setData(uri);
		return intent;

	}

	private static Intent sendWord(Uri uri) {
		return null;
	}

	
	public static Intent getPrintIntent(Uri uri, int fileType) {

		switch (fileType) {
		case PDF1:
			return sendPDFToPrintShare(uri);
		case PDF2:
			return sendPDFToPrinthand(uri);
		case WORD:
			return sendWord(uri);
		case EXCEL:
			return sendExcel(uri);
		default:
			return null;
		}

	}

}
