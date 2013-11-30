package com.loveplusplus.demo.print.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

/**
 * 文件工具类
 * @author Administrator
 *
 */
public class FileUtils {

	/**
	 * 获取cache 缓存目录路径
	 * 
	 * @param context
	 * @return
	 */
	public static String getCacheDir(Context context) {

		return getCacheDir(context, null);
	}

	/**
	 * 获取cache 缓存目录下面的子目录
	 * 
	 * @param context
	 * @param childDirName
	 * @return
	 */
	public static String getCacheDir(Context context, String childDirName) {
		return getDir(context, true, childDirName);
	}

	/**
	 * 获取 files目录
	 * 
	 * @param context
	 * @return
	 */
	public static String getFilesDir(Context context) {
		return getFilesDir(context, null);
	}

	/**
	 * 获取 files 下的子目录
	 * 
	 * @param context
	 * @param childDirName
	 * @return
	 */
	public static String getFilesDir(Context context, String childDirName) {
		return getDir(context, false, childDirName);
	}

	
	
	private static String getDir(Context context, boolean isCache,
			String childDirName) {
		StringBuilder sb = new StringBuilder();
		sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
		sb.append("/Android/data/");
		sb.append(context.getPackageName());
		if (isCache) {
			sb.append("/cache/");
		} else {
			sb.append("/files/");
		}
		if (!TextUtils.isEmpty(childDirName)) {
			sb.append(childDirName);
		}
		
		String path=sb.toString();
	
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return path;
	}

	/**
	 * 根据路径删除文件
	 * 
	 * @param path
	 */
	public static void deleteTempFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 把Assert下的文件拷贝到SDCard /Android/data/应用包名/files 目录下
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static File copyAssetFileToSDCard(Context context, String fileName,
			String dirPath) {
		try {

			InputStream is = context.getAssets().open(fileName);

			File file = new File(dirPath, fileName);
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			byte[] temp = new byte[1024];
			int i = 0;
			while ((i = is.read(temp)) > 0) {
				fos.write(temp, 0, i);
			}
			fos.close();
			is.close();
			return file;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Android/data/包名/files/dirName/prefix_yyyyMMdd_HHmmss.suffix
	 * 
	 * @param context
	 * @param prefix
	 * @param suffix
	 * @param dirName
	 * @return
	 */
	private static File createFileToSDCard(Context context, String prefix,
			String suffix, String dirName) {
		String timeStamp = DateUtil.formatToString(new Date(),
				"yyyyMMdd_HHmmss");

		StringBuilder sb = new StringBuilder();
		sb.append(prefix);
		sb.append("_");
		sb.append(timeStamp);
		sb.append(".");
		sb.append(suffix);

		String dir = getFilesDir(context, dirName);

		return new File(dir, sb.toString());

	}

	
	 /**
     * Check if external storage is built-in or removable.
     *
     * @return True if external storage is removable (like an SD card), false
     *         otherwise.
     */
    @TargetApi(9)
    public static boolean isExternalStorageRemovable() {
        if (Utils.hasGingerbread()) {
            return Environment.isExternalStorageRemovable();
        }
        return true;
    }

    /**
     * Get the external app cache directory.
     *
     * @param context The context to use
     * @return The external cache dir
     */
    @TargetApi(8)
    public static File getExternalCacheDir(Context context) {
        if (Utils.hasFroyo()) {
            return context.getExternalCacheDir();
        }

        // Before Froyo we need to construct the external cache dir ourselves
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }

    /**
     * Check how much usable space is available at a given path.
     *
     * @param path The path to check
     * @return The space available in bytes
     */
    @TargetApi(9)
    public static long getUsableSpace(File path) {
        if (Utils.hasGingerbread()) {
            return path.getUsableSpace();
        }
        final StatFs stats = new StatFs(path.getPath());
        return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
    }
	
	public static File createPDFToSDCard(Context context, String prefix) {
		return createFileToSDCard(context, prefix, "pdf", "pdf");
	}

	public static File createWordToSDCard(Context context, String prefix) {
		return createFileToSDCard(context, prefix, "doc", "word");
	}

	public static File createExcelToSDCard(Context context, String prefix) {
		return createFileToSDCard(context, prefix, "xls", "excel");
	}
}
