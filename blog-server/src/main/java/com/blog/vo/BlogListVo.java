package com.blog.vo;

import java.util.ArrayList;
import java.util.List;

import com.blog.models.Blog;

public class BlogListVo {

	private List<Blog> list = new ArrayList<Blog>();

	private int pagecount;

	public List<Blog> getList() {
		return list;
	}

	public void setList(List<Blog> list) {
		this.list = list;
	}

	public int getPagecount() {
		return pagecount;
	}

	public void setPagecount(int pagecount) {
		this.pagecount = pagecount;
	}


}
