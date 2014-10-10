package com.blog.vo;

import java.util.ArrayList;
import java.util.List;

import com.blog.models.Category;

public class CategoryVo {

	private List<CategoryOneVo> list = new ArrayList<CategoryOneVo>();

	public List<CategoryOneVo> getList() {
		return list;
	}

	public void setList(List<CategoryOneVo> list) {
		this.list = list;
	}



}
