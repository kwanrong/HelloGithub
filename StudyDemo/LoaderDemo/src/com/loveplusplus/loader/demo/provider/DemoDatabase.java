package com.loveplusplus.loader.demo.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.loveplusplus.loader.demo.provider.DemoContract.CityColumns;
import com.loveplusplus.loader.demo.provider.DemoContract.CountyColumns;
import com.loveplusplus.loader.demo.provider.DemoContract.ProvinceColumns;

/**
 * Android数据库类， 当程序中第一次使用数据库时，系统会调用这个类的构造方法，创建数据库
 * 数据库的名称和版本是DATABASE_NAME和DATABASE_VERSION 然后调用onCreate方法 我们可以在这个方法中编写创建表的SQL
 * 当应用升级，我们可以把数据库的版本号改大一些（一般+1），系统发现版本号变大，系统会 onUpgrade方法。
 * 
 * @author feicien
 * 
 */
public class DemoDatabase extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "demo.db";
	private static final int DATABASE_VERSION = 2;
	private static final String TAG = "DemoDatabase";

	public DemoDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * 定义数据库中的表名常量 使用这种方式，是我参考一些源码中的写法 这样的好处是:易读性比较好，比如：Tables.PROVINCE
	 * 
	 * @author feicien
	 * 
	 */
	interface Tables {
		String PROVINCE = "province";
		String CITY = "city";
		String COUNTY = "county";
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "创建数据库表");
		db.execSQL("CREATE TABLE " + Tables.PROVINCE + "("
				+ BaseColumns._ID + "  INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ ProvinceColumns.CODE + "  TEXT NOT NULL,"
				+ ProvinceColumns.NAME + "  TEXT NOT NULL,"
				+"UNIQUE ("+ProvinceColumns.CODE+" ) ON CONFLICT REPLACE)");
		
		db.execSQL("CREATE TABLE " + Tables.CITY + "("
				+ BaseColumns._ID + "  INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ CityColumns.CODE + "  TEXT NOT NULL,"
				+ CityColumns.NAME + "  TEXT NOT NULL,"
				+ CityColumns.PROVINCE_CODE + "  TEXT NOT NULL,"
				+"UNIQUE ("+CityColumns.CODE+" ) ON CONFLICT REPLACE)");
		
		db.execSQL("CREATE TABLE " + Tables.COUNTY + "("
				+ BaseColumns._ID + "  INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ CountyColumns.CODE + "  TEXT NOT NULL,"
				+ CountyColumns.NAME + "  TEXT NOT NULL,"
				+ CountyColumns.CITY_CODE + "  TEXT NOT NULL,"
				+"UNIQUE ("+CountyColumns.CODE+" ) ON CONFLICT REPLACE)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "升级数据库：从版本" + oldVersion + "升级到" + newVersion);
		db.execSQL("DROP TABLE IF EXISTS " + Tables.PROVINCE);
		db.execSQL("DROP TABLE IF EXISTS " + Tables.CITY);
		db.execSQL("DROP TABLE IF EXISTS " + Tables.COUNTY);
		onCreate(db);
	}
}
