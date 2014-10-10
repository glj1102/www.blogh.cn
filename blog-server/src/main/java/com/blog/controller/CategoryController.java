package com.blog.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import com.avaje.ebean.Ebean;
import com.blog.accept.bean.ResultBean;
import com.blog.models.Category;
import com.blog.service.CategoryService;
import com.blog.vo.CategoryOneListVo;
import com.blog.vo.CategoryVo;

@Path("/category")
public class CategoryController {

	public CategoryService categoryService=null;

	@POST
	@Path("/create.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResultBean importProduct(Category category){
		try {
			Ebean.save(category);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResultBean("ok");
	}

	@GET
	@Path("/tree.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CategoryVo getList(){
		categoryService = CategoryService.getInstance();
		CategoryVo categoryVo=categoryService.list();
		return categoryVo;
	}

	@GET
	@Path("/one.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CategoryOneListVo getOnes(){
		categoryService = CategoryService.getInstance();
		CategoryOneListVo colv=categoryService.listone();
		return colv;
	}

	@GET
	@Path("/two.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CategoryOneListVo gettwos(@Context HttpHeaders headers){
		String category_1 = headers.getRequestHeader("category_1").get(0);
		categoryService = CategoryService.getInstance();
		CategoryOneListVo colv=categoryService.listtwo(category_1);
		return colv;
	}

	@DELETE
	@Path("/delete.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResultBean deleteProduct(@Context HttpHeaders headers){
		long id=Long.parseLong(headers.getRequestHeader("id").get(0));
		categoryService = CategoryService.getInstance();
		categoryService.delete(id);
		return new ResultBean("ok");
	}
}
