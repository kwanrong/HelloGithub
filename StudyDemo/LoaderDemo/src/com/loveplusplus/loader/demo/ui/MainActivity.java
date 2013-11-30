package com.loveplusplus.loader.demo.ui;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.loveplusplus.loader.demo.R;
import com.loveplusplus.loader.demo.provider.DemoContract.City;
import com.loveplusplus.loader.demo.provider.DemoContract.County;
import com.loveplusplus.loader.demo.provider.DemoContract.Province;
import com.loveplusplus.loader.demo.util.ServerUtilities;

public class MainActivity extends Activity implements
		LoaderManager.LoaderCallbacks<Cursor> {
	protected static final String TAG = "MainActivity";
	/**
	 * loader id
	 */
	private static final int LOADER_PROVINCE_ID = 0;
	private static final int LOADER_CITY_ID = 1;
	private static final int LOADER_COUNTY_ID = 2;

	private static final String HOST_URL="http://xiangxiangmeishi.sinaapp.com/";
	private static final String PROVINCE_URL = HOST_URL+"api/province";
	private static final String CITY_URI = HOST_URL+"api/city";
	private static final String COUNTY_URL = HOST_URL+"api/county";

	private Spinner provinceSpinner;
	private Spinner citySpinner;
	private Spinner countySpinner;
	private SimpleCursorAdapter provinceAdapter;
	private SimpleCursorAdapter cityAdapter;
	private SimpleCursorAdapter countyAdapter;

	private String provinceCode;
	private String cityCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpView();

		// 首先初始省份的loader
		getLoaderManager().initLoader(LOADER_PROVINCE_ID, null, this);
		getLoaderManager().initLoader(LOADER_CITY_ID, null, this);
		getLoaderManager().initLoader(LOADER_COUNTY_ID, null, this);

		getContentResolver().registerContentObserver(Province.CONTENT_URI,
				true, new ContentObserver(new Handler()) {
					@Override
					public void onChange(boolean selfChange) {

						Loader<Cursor> loader1 = getLoaderManager().getLoader(
								LOADER_PROVINCE_ID);
						if (loader1 != null) {
							loader1.forceLoad();
						}
					}
				});
		getContentResolver().registerContentObserver(City.CONTENT_URI,
				true, new ContentObserver(new Handler()) {
			@Override
			public void onChange(boolean selfChange) {
				
				Loader<Cursor> loader1 = getLoaderManager().getLoader(
						LOADER_CITY_ID);
				if (loader1 != null) {
					loader1.forceLoad();
				}
			}
		});
		getContentResolver().registerContentObserver(County.CONTENT_URI,
				true, new ContentObserver(new Handler()) {
			@Override
			public void onChange(boolean selfChange) {
				
				Loader<Cursor> loader1 = getLoaderManager().getLoader(
						LOADER_COUNTY_ID);
				if (loader1 != null) {
					loader1.forceLoad();
				}
			}
		});

	}

	private void setUpView() {
		provinceSpinner = (Spinner) findViewById(R.id.spinner_province);
		provinceAdapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_1, null,
				new String[] { Province.NAME },
				new int[] { android.R.id.text1 }, 0);
		provinceSpinner.setAdapter(provinceAdapter);
		provinceSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				//这2行是为了解决选择 台湾 香港 澳门时，没有城市，却有县的问题
				cityAdapter.swapCursor(null);
				countyAdapter.swapCursor(null);
				
				Cursor item = (Cursor) provinceAdapter.getItem(position);
				provinceCode = item.getString(item
						.getColumnIndex(Province.CODE));
				updateCity(provinceCode);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		citySpinner = (Spinner) findViewById(R.id.spinner_city);
		cityAdapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_1, null,
				new String[] { City.NAME }, new int[] { android.R.id.text1 }, 0);
		citySpinner.setAdapter(cityAdapter);
		citySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Cursor item = (Cursor) cityAdapter.getItem(position);
				cityCode = item.getString(item.getColumnIndex(City.CODE));
				updateCounty(cityCode);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		

		countySpinner = (Spinner) findViewById(R.id.spinner_county);
		countyAdapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_1, null,
				new String[] { County.NAME }, new int[] { android.R.id.text1 },
				0);
		countySpinner.setAdapter(countyAdapter);

	}

	protected void updateCounty(String cityCode) {
		Bundle args = new Bundle();
		args.putString("city_code", cityCode);
		getLoaderManager().restartLoader(LOADER_COUNTY_ID, args, this);
	}

	protected void updateCity(String provinceCode) {
		Bundle args = new Bundle();
		args.putString("province_code", provinceCode);
		getLoaderManager().restartLoader(LOADER_CITY_ID, args, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_download_data:
			loadProvices();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void loadProvices() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					// 获取省份数据
					String json = ServerUtilities.post(PROVINCE_URL, null);
					Log.d(TAG, json);
					JSONArray array = new JSONArray(json);
					ContentValues[] values = new ContentValues[array.length()];
					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = (JSONObject) array.get(i);
						ContentValues v = new ContentValues();
						v.put(Province.CODE, obj.getString("code"));
						v.put(Province.NAME, obj.getString("name"));
						values[i] = v;
					}
					// 保存到数据库
					getContentResolver().bulkInsert(Province.CONTENT_URI,
							values);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		}).start();
	}

	private void loadCitys(final String provinceCode) {
		if (null == provinceCode)
			return;

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					HashMap<String,String> params=new HashMap<String, String>();
					params.put("province_code", provinceCode);
					// 获取城市数据
					String json = ServerUtilities.post(CITY_URI, params);
					Log.d(TAG, json);
					JSONArray array = new JSONArray(json);
					ContentValues[] values = new ContentValues[array.length()];
					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = (JSONObject) array.get(i);
						ContentValues v = new ContentValues();
						v.put(City.CODE, obj.getString("code"));
						v.put(City.NAME, obj.getString("name"));
						v.put(City.PROVINCE_CODE, obj.getString("provinceCode"));
						values[i] = v;
					}
					// 保存到数据库
					getContentResolver().bulkInsert(City.CONTENT_URI, values);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		}).start();
	}

	private void loadCountys(final String cityCode) {

		if (null == cityCode)
			return;
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					HashMap<String,String> params=new HashMap<String, String>();
					params.put("city_code", cityCode);
					
					String json = ServerUtilities.post(COUNTY_URL, params);
					Log.d(TAG, json);
					JSONArray array = new JSONArray(json);
					ContentValues[] values = new ContentValues[array.length()];
					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = (JSONObject) array.get(i);
						ContentValues v = new ContentValues();
						v.put(County.CODE, obj.getString("code"));
						v.put(County.NAME, obj.getString("name"));
						v.put(County.CITY_CODE, obj.getString("cityCode"));
						values[i] = v;
					}
					// 保存到数据库
					getContentResolver().bulkInsert(County.CONTENT_URI, values);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		}).start();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		//
		switch (id) {
		case LOADER_PROVINCE_ID:
			return new CursorLoader(this, Province.CONTENT_URI, null, null,
					null, null);
		case LOADER_CITY_ID:
			String provinceCode = null == args ? "" : args
					.getString("province_code");
			return new CursorLoader(this, City.CONTENT_URI, null,
					City.PROVINCE_CODE + "=?", new String[] { provinceCode },
					null);

		case LOADER_COUNTY_ID:
			String cityCode = args == null ? "" : args.getString("city_code");
			return new CursorLoader(this, County.CONTENT_URI, null,
					County.CITY_CODE + "=?", new String[] { cityCode }, null);

		default:
			return null;
		}
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		int id = loader.getId();
		switch (id) {
		case LOADER_PROVINCE_ID:
			provinceAdapter.swapCursor(data);
			break;
		case LOADER_CITY_ID:
			// 如果本地没有数据，就从网络上加载数据
			if (null == data||data.getCount()<1) {
				loadCitys(provinceCode);
			}
			cityAdapter.swapCursor(data);
			break;
		case LOADER_COUNTY_ID:
			if (null == data||data.getCount()<1) {
				loadCountys(cityCode);
			}
			countyAdapter.swapCursor(data);
			break;
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		int id = loader.getId();
		switch (id) {
		case 0:
			provinceAdapter.swapCursor(null);
			break;
		case 1:
			cityAdapter.swapCursor(null);
			break;
		case 2:
			countyAdapter.swapCursor(null);
			break;
		}
	}

}
