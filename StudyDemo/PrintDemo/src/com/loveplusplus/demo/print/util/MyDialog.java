package com.loveplusplus.demo.print.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.loveplusplus.demo.print.R;

public class MyDialog extends DialogFragment {

	private static MyDialog mDialog;
	private static MyDialogButtonClickListener mListener;

	public static MyDialog newInstance(MyDialogButtonClickListener listener) {
		mListener = listener;
		mDialog = new MyDialog();
		return mDialog;
	}

	public interface MyDialogButtonClickListener {
		void onClickOk(DialogFragment dialog);

		void onClickCancel(DialogFragment dialog);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return new AlertDialog.Builder(getActivity())
				.setIcon(R.drawable.ic_launcher)
				.setTitle("安装打印插件")
				.setMessage("第一次使用打印功能，需要安装打印插件，如果不安装插件便不能使用打印功能")
				.setPositiveButton("安装", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						mListener.onClickOk(mDialog);
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						mListener.onClickCancel(mDialog);
					}
				}).create();
	}

}