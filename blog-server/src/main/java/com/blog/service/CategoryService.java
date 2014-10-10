package com.blog.service;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.blog.models.Category;
import com.blog.vo.CategoryOneListVo;
import com.blog.vo.CategoryOneVo;
import com.blog.vo.CategoryTwoVo;
import com.blog.vo.CategoryVo;

public class CategoryService {

	public static CategoryService categoryService=null;
	public static CategoryService getInstance() {
		if(categoryService==null){
			categoryService=new CategoryService();
		}

		return categoryService;
	}

	/**
	 * 获取所有类别
	 * @return
	 */
	public CategoryVo list(){
		CategoryVo c = new CategoryVo();
		List<Category> onelist = new ArrayList<Category>();
		List<CategoryOneVo> onevolist = new ArrayList<CategoryOneVo>();
		onelist = Ebean.find(Category.class).where().eq("parent_id", 0).findList();
		for(Category category:onelist){
			List<Category> twolist = new ArrayList<Category>();
			List<CategoryTwoVo> twovolist = new ArrayList<CategoryTwoVo>();
			CategoryOneVo cov = new CategoryOneVo();
			cov.setId(category.getId());
			cov.setName(category.getName());
			twolist = Ebean.find(Category.class).where().eq("parent_id", category.getId()).findList();
			for(Category cg : twolist){
				CategoryTwoVo ctv = new CategoryTwoVo();
				ctv.setId(cg.getId());
				ctv.setName(cg.getName());
				twovolist.add(ctv);
			}
			cov.setList(twovolist);
			onevolist.add(cov);
		}
		c.setList(onevolist);
		return c;
	}


	public CategoryOneListVo listone(){
		CategoryOneListVo colv = new CategoryOneListVo();
		List<Category> list = new ArrayList<Category>();
		try {
			list = Ebean.find(Category.class).where().eq("parent_id", 0).findList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		colv.setList(list);
		return colv;
	}



	/**
	 * 批量删除
	 * @param list
	 */
	public void delete(long id){
		List<Category> list = new ArrayList<Category>();
		try {
				list = Ebean.find(Category.class).where().eq("parent_id", id).findList();
				if(list.size()>0){
					Ebean.delete(list);
					Ebean.delete(Category.class, id);
				}else{
					Ebean.delete(Category.class, id);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CategoryOneListVo listtwo(String category_1){
		CategoryOneListVo colv = new CategoryOneListVo();
		List<Category> list = new ArrayList<Category>();
		try {
			list = Ebean.find(Category.class).where().eq("parent_id", category_1).findList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		colv.setList(list);
		return colv;
	}
}
