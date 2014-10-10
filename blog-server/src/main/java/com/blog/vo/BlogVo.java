package com.blog.vo;

import java.util.Date;

public class BlogVo {

	private long id;
	private String user;
	private String email;
	private String blog_title;
	private String blog_content;
	private long category_1;//一级分类id
	private String category_1_name;//一级分类名称
	private long category_2;//二级分类id
	private String category_2_name;//二级分类名称
	private Date create_time;//创建时间
	private int view_count;//阅读数量
	private int comment;//评论个数

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBlog_title() {
		return blog_title;
	}
	public void setBlog_title(String blog_title) {
		this.blog_title = blog_title;
	}
	public String getBlog_content() {
		return blog_content;
	}
	public void setBlog_content(String blog_content) {
		this.blog_content = blog_content;
	}
	public long getCategory_1() {
		return category_1;
	}
	public void setCategory_1(long category_1) {
		this.category_1 = category_1;
	}
	public String getCategory_1_name() {
		return category_1_name;
	}
	public void setCategory_1_name(String category_1_name) {
		this.category_1_name = category_1_name;
	}
	public long getCategory_2() {
		return category_2;
	}
	public void setCategory_2(long category_2) {
		this.category_2 = category_2;
	}
	public String getCategory_2_name() {
		return category_2_name;
	}
	public void setCategory_2_name(String category_2_name) {
		this.category_2_name = category_2_name;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public int getView_count() {
		return view_count;
	}
	public void setView_count(int view_count) {
		this.view_count = view_count;
	}
	public int getComment() {
		return comment;
	}
	public void setComment(int comment) {
		this.comment = comment;
	}

}
