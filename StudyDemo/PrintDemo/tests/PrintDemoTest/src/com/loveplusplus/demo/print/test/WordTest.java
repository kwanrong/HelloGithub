package com.loveplusplus.demo.print.test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.loveplusplus.demo.print.util.FileUtils;
import com.loveplusplus.demo.print.util.WordUtil;

@SmallTest
public class WordTest extends AndroidTestCase {

	@Override
    protected void setUp() throws Exception {
        super.setUp();
        mContext = getContext();
    }
	
	public void test() throws Exception{
		
		File file=FileUtils.createWordToSDCard(mContext,"demo");
		Map<String, String> map = new HashMap<String, String>(); 

		map.put("demo_sno", "20130305001");
		
		map.put("demo_sampling_name", "河南XX实业有限公司");
		map.put("demo_sampling_address", "郑州市XX路XX号");
		map.put("demo_sampling_people", "张三 18812345678");
		
		map.put("demo_production_name", "XXX葡萄糖");
		map.put("demo_production_type", "XXXXXX");
		map.put("demo_production_level", "XXX");
		map.put("demo_production_brand", "欢乐牌");
		map.put("demo_production_standard", "GB-2312");
		map.put("demo_production_sno", "123345");
		map.put("demo_production_date", "2012年03月30日");
		map.put("demo_production_expiration_date", "2014年03月30日");
		
		map.put("demo_sampling_number", "30");
		map.put("demo_sampling_date", "2013年03月");
		
		map.put("demo_company_name", "郑州市XXX公司");
		map.put("demo_company_address", "郑州市xxx路100号");
		map.put("demo_company_phone", "15512345678");
		
		map.put("demo_depart_name", "XXX质量检测局");
		map.put("demo_depart_people", "张三");
		map.put("demo_depart_phone", "18912345678");
		map.put("demo_depart_email", "xxx@xxx.com");
		
		map.put("demo_sampling_remark", "这里是备注");
		
		map.put("demo_sampling_date_1", "2013年05月29日");
		map.put("demo_sampling_date_2", "2013年05月29日");
		map.put("demo_sampling_date_3", "2013年05月29日");
		
		WordUtil.word(getContext(), file,"doc1.doc","demo_",map);
	}
}