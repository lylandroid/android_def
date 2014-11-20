package com.wi360.mobile.wallet.bean;

import java.util.List;

public class NewCenter {
	public List<NewsCenter> data;
	public List<String> extend;
	public int retcode;
	public class NewsCenter {
		public List<ChildrenItem> children;
		public int id ;
		public String title;
		public String type;
		public String url;
		public String url1;
		public String dayurl;
		public String excurl;
		public String weekurl;
	}
	public class ChildrenItem{
		public int id;
		public String title;
		public String type;
		public String url;
	}
	
}





















