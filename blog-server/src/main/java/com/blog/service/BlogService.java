package com.blog.service;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.blog.lucene.LuceneSearch;
import com.blog.models.Blog;
import com.blog.models.Comment;
import com.blog.vo.BlogListVo;
import com.blog.vo.BlogVo;

public class BlogService {

	public static BlogService blogService=null;
	public static BlogService getInstance() {
		if(blogService==null){
			blogService=new BlogService();
		}

		return blogService;
	}


	/**
	 * 批量删除
	 * @param list
	 */
	public void delete(long id){
		Blog b = Ebean.find(Blog.class, id);
		b.setStatus("deleted");
		Ebean.update(b);
	}


	public void view(long id){
		Blog b = Ebean.find(Blog.class, id);
		b.setView_count(b.getView_count()+1);
		Ebean.update(b);
	}

	public BlogListVo getlist(String categoryoneid,String categorytwoid,int curpage,int pagesize){
		List<Blog> list = new ArrayList<Blog>();
		int pagecount = 0;
		BlogListVo blv = new BlogListVo();
		try {
			if("".equals(categoryoneid) && "".equals(categorytwoid)){
				list=Ebean.find(Blog.class).where().eq("status", "normal").orderBy().desc("id").findPagingList(pagesize).getPage(curpage-1).getList();
				pagecount = Ebean.find(Blog.class).where().eq("status", "normal").findPagingList(pagesize).getTotalPageCount();
			}else if("".equals(categoryoneid) && !"".equals(categorytwoid)){
				list=Ebean.find(Blog.class).where().eq("status", "normal").eq("category_2", categorytwoid).orderBy().desc("id").findPagingList(pagesize).getPage(curpage-1).getList();
				pagecount = Ebean.find(Blog.class).where().eq("status", "normal").eq("category_2", categorytwoid).findPagingList(pagesize).getTotalPageCount();
			}else if(!"".equals(categoryoneid) && "".equals(categorytwoid)){
				list=Ebean.find(Blog.class).where().eq("status", "normal").eq("category_1", categoryoneid).orderBy().desc("id").findPagingList(pagesize).getPage(curpage-1).getList();
				pagecount = Ebean.find(Blog.class).where().eq("status", "normal").eq("category_1", categoryoneid).findPagingList(pagesize).getTotalPageCount();
			}else{
				list=Ebean.find(Blog.class).where().eq("status", "normal").eq("category_1", categoryoneid).eq("category_2", categorytwoid).orderBy().desc("id").findPagingList(pagesize).getPage(curpage-1).getList();
				pagecount = Ebean.find(Blog.class).where().eq("status", "normal").eq("category_1", categoryoneid).eq("category_2", categorytwoid).findPagingList(pagesize).getTotalPageCount();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(pagecount==0){
			pagecount=1;
		}
		blv.setList(list);
		blv.setPagecount(pagecount);
		return blv;
	}

	public BlogVo getblog(long id){
		Blog blog = new Blog();
		BlogVo blogvo = new BlogVo();
		int comment=0;
		try {
			List<Blog> list = Ebean.find(Blog.class).where().eq("id", id).eq("status", "normal").findList();
			if(list.size()>0){
				blog = list.get(0);
			}
			comment = Ebean.find(Comment.class).where().eq("blog_id", id).eq("status", "normal").findRowCount();
		} catch (Exception e) {
			e.printStackTrace();
		}

		blogvo.setBlog_content(blog.getBlog_content());
		blogvo.setBlog_title(blog.getBlog_title());
		blogvo.setCategory_1(blog.getCategory_1());
		blogvo.setCategory_1_name(blog.getCategory_1_name());
		blogvo.setCategory_2(blog.getCategory_2());
		blogvo.setCategory_2_name(blog.getCategory_2_name());
		blogvo.setComment(comment);
		blogvo.setCreate_time(blog.getCreate_time());
		blogvo.setEmail(blog.getEmail());
		blogvo.setId(blog.getId());
		blogvo.setUser(blog.getUser());
		blogvo.setView_count(blog.getView_count());
		return blogvo;
	}


	public BlogListVo gethot(){
		List<Blog> list = new ArrayList<Blog>();
		int pagecount = 0;
		BlogListVo blv = new BlogListVo();
		try {
			list=Ebean.find(Blog.class).where().eq("status", "normal").orderBy().desc("view_count").findPagingList(10).getPage(0).getList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(pagecount==0){
			pagecount=1;
		}
		blv.setList(list);
		blv.setPagecount(pagecount);
		return blv;
	}

	public BlogListVo search(int curpage,int pagesize,String keyword){
		LuceneSearch luceneSeearch = new LuceneSearch();
		BlogListVo bloglistVo = luceneSeearch.search(curpage, pagesize, keyword);
		return bloglistVo;
	}

}
