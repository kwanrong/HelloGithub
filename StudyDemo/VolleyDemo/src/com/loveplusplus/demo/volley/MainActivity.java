package com.loveplusplus.demo.volley;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.loveplusplus.demo.volley.BlogResponse.BlogBean;
import com.loveplusplus.demo.volley.BlogResponse.BlogList;

public class MainActivity extends ListActivity implements OnNavigationListener {

	protected static final String TAG = "MainActivity";
	private RequestQueue reqQueue;
	private ImageLoader imageLoader;
	private BlogBean blogBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setDisplayShowTitleEnabled(false);

		reqQueue = Volley.newRequestQueue(this);
		imageLoader = new ImageLoader(reqQueue, new BitmapLruCache());

		GsonRequest<BlogResponse> jr = new GsonRequest<BlogResponse>(
				"http://api.eoe.cn/client/blog?k=lists", BlogResponse.class,
				null, new Response.Listener<BlogResponse>() {

					@Override
					public void onResponse(BlogResponse response) {
						Log.d(TAG, response.toString());

						blogBean = response.getResponse();
						List<BlogList> list = blogBean.getList();
						int size = blogBean.getList().size();
						
						String[] vs = new String[size];
						for(int i=0;i<size;i++){
							vs[i]=list.get(i).getName();
						}
						ArrayAdapter<String> adapter = new ArrayAdapter<String>(
								MainActivity.this,
								android.R.layout.simple_list_item_1, vs);

						actionBar.setListNavigationCallbacks(adapter,
								MainActivity.this);

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.d(TAG, error.toString());
					}
				});

		reqQueue.add(jr);

	}

	protected void changeData(List<Blog> list) {
		MyListAdapter adapter = new MyListAdapter(MainActivity.this, list,
				imageLoader);
		setListAdapter(adapter);
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {

		changeData(blogBean.getList().get(itemPosition).getItems());
		return true;
	}

}
