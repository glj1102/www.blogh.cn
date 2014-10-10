package com.blog.vo;

import java.util.ArrayList;
import java.util.List;

import com.blog.models.Comment;

public class CommentListVo {

	private List<Comment> list = new ArrayList<Comment>();

	private int pagecount;
	private int curpage;

	public List<Comment> getList() {
		return list;
	}

	public void setList(List<Comment> list) {
		this.list = list;
	}

	public int getPagecount() {
		return pagecount;
	}

	public void setPagecount(int pagecount) {
		this.pagecount = pagecount;
	}

	public int getCurpage() {
		return curpage;
	}

	public void setCurpage(int curpage) {
		this.curpage = curpage;
	}


}
