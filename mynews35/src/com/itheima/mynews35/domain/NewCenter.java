package com.itheima.mynews35.domain;

import java.util.List;

/**
 *
 * 
 * @author Administrator
 * 
 */
public class NewCenter {
	public int retcode;
	public List<NewsCenter> data;
	public List<String> extend;

	public class NewsCenter {
		public List<ChildrenItem> children;
		public int id;
		public String title;
		public int type;
		public String url;
		public String url1;
		public String dayurl;
		public String excurl;
		public String weekurl;
	}

	public class ChildrenItem {
		public int id;
		public String title;
		public int type;
		public String url;
	}
}
