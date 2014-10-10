package com.blog.vo;

import java.util.ArrayList;
import java.util.List;

import com.blog.models.UserMail;

public class EmailVo {

	private List<UserMail> list = new ArrayList<UserMail>();
	private int pagecount;
	public List<UserMail> getList() {
		return list;
	}
	public void setList(List<UserMail> list) {
		this.list = list;
	}
	public int getPagecount() {
		return pagecount;
	}
	public void setPagecount(int pagecount) {
		this.pagecount = pagecount;
	}


}
