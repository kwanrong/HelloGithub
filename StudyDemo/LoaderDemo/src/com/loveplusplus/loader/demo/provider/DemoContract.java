package com.loveplusplus.loader.demo.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * 这个类中定义了一些常量。 比如数据库中表的字段。 ContentProvider 的URI
 * 
 * @author feicien
 * 
 */
public class DemoContract {

	/**
	 * 省份 表的字段
	 * 
	 * @author feicien
	 * 
	 */
	interface ProvinceColumns {
		/*
		 * 省份编码
		 */
		String CODE = "province_code";
		/*
		 * 省份名称
		 */
		String NAME = "province_name";
	}

	/**
	 * 城市 表的字段
	 * 
	 * @author feicien
	 * 
	 */
	interface CityColumns {
		/*
		 * 城市代码
		 */
		String CODE = "city_code";
		/*
		 * 城市的名称
		 */
		String NAME = "city_name";
		/*
		 * 城市所属那个省份
		 */
		String PROVINCE_CODE = "province_code";
	}

	/**
	 * 县 表的字段
	 * 
	 * @author feicien
	 * 
	 */
	interface CountyColumns {
		/*
		 * 县代码
		 */
		String CODE = "county_code";
		/*
		 * 县名称
		 */
		String NAME = "county_name";
		/*
		 * 县所属那个城市
		 */
		String CITY_CODE = "city_code";
	}

	/*
	 * 定义ContentProvider的URI
	 */
	public static final String CONTENT_AUTHORITY = "com.loveplusplus.loader.demo.provider.DemoProvider";
	public static final Uri BASE_CONTENT_URI = Uri.parse("content://"
			+ CONTENT_AUTHORITY);

	public static final String PATH_PROVINCE = "province";
	public static final String PATH_CITY = "city";
	public static final String PATH_COUNTY = "county";

	/**
	 * 这个类相当与JavaBean 在android中不推荐使用javabean, 应用使用javabean还得设置setter和getter方法
	 * 该类实现ProvinceColumns接口，所以它可以使用province这个表中的字段
	 * 实现BaseColumns这个接口，它就有个_id,_count这2个字段
	 * 
	 * @author feicien
	 * 
	 */
	public static class Province implements ProvinceColumns, BaseColumns {
		/*
		 * 省的uri 使用这个uri，可以查询省份相关的数据
		 */
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_PROVINCE).build();

		/**
		 * 省份数据的类型，你要查询的是一条信息，还是一个集合
		 */
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.demo.province";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.demo.province";

		/**
		 * 根据id构建uri
		 * 
		 * @param proviceId
		 * @return
		 */
		public static Uri buildProvinceUri(String proviceId) {
			return CONTENT_URI.buildUpon().appendPath(proviceId).build();
		}

		/**
		 * 更加提供的uri,获取id
		 * 
		 * @param uri
		 * @return
		 */
		public static String getProvinceId(Uri uri) {
			return uri.getLastPathSegment();
		}
	}

	public static class City implements CityColumns, BaseColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_CITY).build();

		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.demo.city";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.demo.city";

		public static Uri buildCityUri(String cityId) {
			return CONTENT_URI.buildUpon().appendPath(cityId).build();
		}

		public static String getCityId(Uri uri) {
			return uri.getLastPathSegment();
		}
	}

	public static class County implements CountyColumns, BaseColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_COUNTY).build();

		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.demo.county";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.demo.county";

		public static Uri buildCountyUri(String countyId) {
			return CONTENT_URI.buildUpon().appendPath(countyId).build();
		}

		public static String getCountyId(Uri uri) {
			return uri.getLastPathSegment();
		}
	}

}
