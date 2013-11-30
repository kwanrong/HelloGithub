package com.loveplusplus.loader.demo.provider;

import java.util.Arrays;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import com.loveplusplus.loader.demo.provider.DemoContract.City;
import com.loveplusplus.loader.demo.provider.DemoContract.County;
import com.loveplusplus.loader.demo.provider.DemoContract.Province;
import com.loveplusplus.loader.demo.provider.DemoDatabase.Tables;

public class DemoProvider extends ContentProvider {

	private DemoDatabase mOpenHelper;

	private static final UriMatcher sUriMatcher = buildUriMatcher();

	private static final int PROVINCES = 101;
	private static final int PROVINCE = 102;

	private static final int CITYS = 201;
	private static final int CITY = 202;

	private static final int COUNTYS = 301;
	private static final int COUNTY = 302;

	private static final String TAG = "DemoProvider";

	private static UriMatcher buildUriMatcher() {
		final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

		final String authority = DemoContract.CONTENT_AUTHORITY;

		/*
		 * 查询所以的省份
		 */
		matcher.addURI(authority, DemoContract.PATH_PROVINCE, PROVINCES);
		/*
		 * 某一个省份
		 */
		matcher.addURI(authority, DemoContract.PATH_PROVINCE + "/*", PROVINCE);

		matcher.addURI(authority, DemoContract.PATH_CITY, CITYS);
		matcher.addURI(authority, DemoContract.PATH_CITY + "/*", CITY);

		matcher.addURI(authority, DemoContract.PATH_COUNTY, COUNTYS);
		matcher.addURI(authority, DemoContract.PATH_COUNTY + "/*", COUNTY);

		return matcher;
	}

	@Override
	public boolean onCreate() {
		mOpenHelper = new DemoDatabase(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		Log.d(TAG, "query(uri=" + uri + ", proj=" + Arrays.toString(projection)
				+ ")");
		final int match = sUriMatcher.match(uri);
		switch (match) {
		case PROVINCES:
			return mOpenHelper.getReadableDatabase()
					.query(Tables.PROVINCE,
							new String[] { Province._ID, Province.CODE,
									Province.NAME }, selection, selectionArgs,
							null, null, null);
		case CITYS:
			return mOpenHelper.getReadableDatabase().query(
					Tables.CITY,
					new String[] { City._ID, City.CODE, City.NAME,
							City.PROVINCE_CODE }, selection, selectionArgs,
					null, null, null);

		case COUNTYS:
			return mOpenHelper.getReadableDatabase().query(
					Tables.COUNTY,
					new String[] { County._ID, County.CODE, County.NAME,
							County.CITY_CODE }, selection, selectionArgs, null,
					null, null);

		default:
			throw new IllegalArgumentException("Unknown URL " + uri);
		}
	}

	@Override
	public String getType(Uri uri) {
		final int match = sUriMatcher.match(uri);
		switch (match) {
		case PROVINCES:
			return Province.CONTENT_TYPE;
		case PROVINCE:
			return Province.CONTENT_ITEM_TYPE;
		case CITYS:
			return City.CONTENT_TYPE;
		case CITY:
			return City.CONTENT_ITEM_TYPE;
		case COUNTYS:
			return County.CONTENT_TYPE;
		case COUNTY:
			return County.CONTENT_ITEM_TYPE;
		default:
			throw new IllegalArgumentException("Unknown URL " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		Log.d(TAG, "insert(uri=" + uri );
		final int match = sUriMatcher.match(uri);
		switch (match) {
		case PROVINCES:
			db.insertOrThrow(Tables.PROVINCE, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Province.buildProvinceUri(values.getAsString(BaseColumns._ID));
		case CITYS:
			db.insertOrThrow(Tables.CITY, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return City.buildCityUri(values.getAsString(BaseColumns._ID));
		case COUNTYS:
			db.insertOrThrow(Tables.COUNTY, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return County.buildCountyUri(values.getAsString(BaseColumns._ID));
		default:
			throw new IllegalArgumentException("Unknown URL " + uri);
		}
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		throw new IllegalArgumentException("Unknown URL " + uri);
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		throw new IllegalArgumentException("Unknown URL " + uri);
	}

}
