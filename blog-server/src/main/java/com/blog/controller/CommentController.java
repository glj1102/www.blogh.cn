package com.blog.controller;

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

import com.avaje.ebean.Ebean;
import com.blog.accept.bean.ResultBean;
import com.blog.models.Comment;
import com.blog.service.CommentService;
import com.blog.vo.CommentListVo;

@Path("/comment")
public class CommentController {
	public CommentService commentService=null;

	@POST
	@Path("/create.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResultBean addComment(Comment comment){
		try {
			comment.setCreate_time(new Date());
			comment.setStatus("normal");
			Ebean.save(comment);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResultBean("ok");
	}

	@DELETE
	@Path("/delete.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResultBean delete(@Context HttpHeaders headers){
		long id=Long.parseLong(headers.getRequestHeader("id").get(0));
		commentService = CommentService.getInstance();
		commentService.delete(id);
		return new ResultBean("ok");
	}


	@GET
	@Path("/list.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CommentListVo list(@Context HttpHeaders headers){
		long blog_id = Long.parseLong(headers.getRequestHeader("blog_id").get(0));
		int curpage = Integer.parseInt(headers.getRequestHeader("curpage").get(0));
		int pagesize = Integer.parseInt(headers.getRequestHeader("pagesize").get(0));
		commentService = CommentService.getInstance();
		CommentListVo clv = commentService.getlist(blog_id,curpage,pagesize);
		return clv;
	}

}
