package com.loveplusplus.demo.print.test;

import java.io.File;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.loveplusplus.demo.print.util.FileUtils;
import com.loveplusplus.demo.print.util.PDFUtils;

@SmallTest
public class TableTest extends AndroidTestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mContext = getContext();
	}

	public void test() throws Exception {
		File distFile = FileUtils.createPDFToSDCard(mContext, "two");

		PDFUtils.test2(mContext, distFile, null);
	}

}
