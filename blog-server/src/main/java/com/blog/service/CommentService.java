package com.blog.service;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.blog.models.Comment;
import com.blog.vo.CommentListVo;

public class CommentService {

	public static CommentService commentService=null;
	public static CommentService getInstance() {
		if(commentService==null){
			commentService=new CommentService();
		}

		return commentService;
	}


	/**
	 * 批量删除
	 * @param list
	 */
	public void delete(long id){
		Comment c = Ebean.find(Comment.class, id);
		c.setStatus("deleted");
		Ebean.update(c);
	}


	public CommentListVo getlist(long blog_id,int curpage,int pagesize){
		List<Comment> list = new ArrayList<Comment>();
		CommentListVo clv = new CommentListVo();
		int pagecount = 0;
		try {
			list = Ebean.find(Comment.class).where().eq("blog_id", blog_id).eq("status", "normal").orderBy().desc("id").findPagingList(pagesize).getPage(curpage-1).getList();
			pagecount = Ebean.find(Comment.class).where().eq("blog_id", blog_id).eq("status", "normal").findPagingList(pagesize).getTotalPageCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(pagecount==0){
			pagecount=1;
		}
		clv.setList(list);
		clv.setPagecount(pagecount);
		clv.setCurpage(curpage);
		return clv;
	}


}
