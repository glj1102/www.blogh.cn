package com.blog.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.apache.lucene.search.TopDocs;

import com.avaje.ebean.Ebean;
import com.blog.accept.bean.ResultBean;
import com.blog.lucene.LuceneIndex;
import com.blog.lucene.LuceneSearch;
import com.blog.models.Blog;
import com.blog.service.BlogService;
import com.blog.vo.BlogListVo;
import com.blog.vo.BlogVo;

@Path("/blog")
public class BlogController {
	public BlogService blogService=null;

	@POST
	@Path("/create.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Blog addBlog(Blog blog){
		try {
			blog.setCreate_time(new Date());
			blog.setStatus("normal");
			Ebean.save(blog);
			//添加索引
			LuceneIndex index = new LuceneIndex();
			index.writeToIndex(blog);
			index.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return blog;
	}

	@DELETE
	@Path("/delete.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResultBean delete(@Context HttpHeaders headers) throws Exception{
		long id=Long.parseLong(headers.getRequestHeader("id").get(0));
		blogService = BlogService.getInstance();
		blogService.delete(id);
		LuceneIndex index = new LuceneIndex();
		index.delete(id);
		index.close();
		return new ResultBean("ok");
	}


	@GET
	@Path("/list.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BlogListVo list(@Context HttpHeaders headers){
		String categoryoneid = headers.getRequestHeader("categoryoneid").get(0);
		String categorytwoid = headers.getRequestHeader("categorytwoid").get(0);
		int curpage = Integer.parseInt(headers.getRequestHeader("curpage").get(0));
		int pagesize = Integer.parseInt(headers.getRequestHeader("pagesize").get(0));
		blogService = BlogService.getInstance();
		BlogListVo bloglistvo = blogService.getlist(categoryoneid,categorytwoid,curpage,pagesize);
		return bloglistvo;
	}


	@GET
	@Path("/view/count.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResultBean viewcount(@Context HttpHeaders headers){
		long id=Long.parseLong(headers.getRequestHeader("id").get(0));
		blogService = BlogService.getInstance();
		blogService.view(id);
		return new ResultBean("ok");
	}

	@GET
	@Path("/getblog.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BlogVo getblog(@Context HttpHeaders headers){
		long id=Long.parseLong(headers.getRequestHeader("id").get(0));
		blogService = BlogService.getInstance();
		BlogVo blogVo = blogService.getblog(id);
		return blogVo;
	}

	@GET
	@Path("/hot.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BlogListVo hot(){
		blogService = BlogService.getInstance();
		BlogListVo blogVo = blogService.gethot();
		return blogVo;
	}

	@GET
	@Path("/search.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BlogListVo search(@Context HttpHeaders headers) throws UnsupportedEncodingException{
		int curpage = Integer.parseInt(headers.getRequestHeader("curpage").get(0));
		int pagesize = Integer.parseInt(headers.getRequestHeader("pagesize").get(0));
		String keyword = headers.getRequestHeader("keyword").get(0);
		String key = URLDecoder.decode(keyword,"utf-8");
		blogService = BlogService.getInstance();
		BlogListVo bloglistvo = blogService.search(curpage,pagesize,key);
		return bloglistvo;
	}

}
