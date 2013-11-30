package com.loveplusplus.demo.volley;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class BlogResponse {

	private BlogBean response;

	public BlogBean getResponse() {
		return response;
	}

	public void setResponse(BlogBean response) {
		this.response = response;
	}

	public class BlogBean {

		private String date;
		private List<Category> categorys;
		private List<BlogList> list;

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public List<Category> getCategorys() {
			return categorys;
		}

		public void setCategorys(List<Category> categorys) {
			this.categorys = categorys;
		}

		public List<BlogList> getList() {
			return list;
		}

		public void setList(List<BlogList> list) {
			this.list = list;
		}

		
	}

	
	public class BlogList{
		private String	name;
		@SerializedName("moreUrl")
		private String moreUrl;
		private List<Blog> items;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		public String getMoreUrl() {
			return moreUrl;
		}
		public void setMoreUrl(String moreUrl) {
			this.moreUrl = moreUrl;
		}
		public List<Blog> getItems() {
			return items;
		}
		public void setItems(List<Blog> items) {
			this.items = items;
		}
		
		
	}
}
