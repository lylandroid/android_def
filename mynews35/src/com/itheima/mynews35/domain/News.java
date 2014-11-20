package com.itheima.mynews35.domain;

import java.util.List;
/**
 * 新闻bean
 * @author Administrator
 *
 */
public class News {
	public NewsBlock data;
	public int retcode;
	
	/**
	 * 新闻每一个版块,如(北京,中国....)
	 * @author Administrator
	 *
	 */
	public class NewsBlock{
		public List<NewsItem> news;
		public List<Topic> topic;
		public List<TopNews> topnews;
		
		
		public String countcommenturl;
		/**
		 * 更多数据
		 */
		public String more;
		public String title;
		
		
	}
	/**
	 * 新闻item
	 * @author Administrator
	 *
	 */
	public class NewsItem{
		public boolean comment;
		public String commentlist;
		public String commenturl;
		public int id;
		public String listimage;
		public String pubdate;
		public String title;
		public String type;
		public String url;
		
		public boolean is_read;
	}
	/**
	 * 主题
	 * @author Administrator
	 *
	 */
	public class Topic{
		public String description;
		public int id ;
		public String listimage;
		public int sort;
		public String title;
		public String url;
	}
	/**
	 * 头条新闻
	 * @author Administrator
	 *
	 */
	public class TopNews{
		public boolean comment;
		public String commentlist;
		public String commenturl;
		public int id ;
		public String pubdate;
		public String title;
		public String topimage;
		public String type;
		public String url;
	}
}
