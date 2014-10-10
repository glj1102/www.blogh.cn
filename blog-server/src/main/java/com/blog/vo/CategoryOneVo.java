package com.blog.vo;

import java.util.ArrayList;
import java.util.List;

import com.blog.models.Category;

public class CategoryOneVo {

	private long id;
	private String name;
	private List<CategoryTwoVo> list = new ArrayList<CategoryTwoVo>();
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<CategoryTwoVo> getList() {
		return list;
	}
	public void setList(List<CategoryTwoVo> list) {
		this.list = list;
	}

}
