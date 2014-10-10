package com.blog.service;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.blog.models.UserMail;
import com.blog.util.ConfigUtil;
import com.blog.util.DES;
import com.blog.util.GmailSender;
import com.blog.vo.EmailVo;

public class MailService {

	public static MailService mailService=null;
	public static MailService getInstance() {
		if(mailService==null){
			mailService=new MailService();
		}

		return mailService;
	}

	public UserMail get(String email){
		List<UserMail> list = new ArrayList<UserMail>();
		try {
			list = Ebean.find(UserMail.class).where().eq("email", email).findList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}

	}
/**
 * 修改邮箱状态
 * @param id
 */
	public void change(long id){
		try {
			UserMail user = Ebean.find(UserMail.class, id);
			if("normal".equals(user.getStatus())){
				user.setStatus("cancel");
			}else{
				user.setStatus("normal");
			}
			Ebean.update(user);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public EmailVo list(String email,int curpage,int pagesize){
		EmailVo ev = new EmailVo();
		List<UserMail> list = new ArrayList<UserMail>();
		int pagecount=0;
		try {
			if("".equals(email)){
				list = Ebean.find(UserMail.class).orderBy().asc("id").findPagingList(pagesize).getPage(curpage-1).getList();
				pagecount = Ebean.find(UserMail.class).findPagingList(pagesize).getTotalPageCount();
			}else{
				list = Ebean.find(UserMail.class).where().eq("email", email).orderBy().asc("id").findPagingList(pagesize).getPage(curpage-1).getList();
				pagecount = Ebean.find(UserMail.class).where().eq("email", email).findPagingList(pagesize).getTotalPageCount();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(pagecount==0){
			pagecount=1;
		}
		ev.setList(list);
		ev.setPagecount(pagecount);
		return ev;
	}
	/**
	 * 发送邮件
	 * @param email
	 */
	public void send(String email){
		try {
			DES des = new DES();
			String host = ConfigUtil.readValue("client_server");
			String url = host+"?message="+des.encrypt(email);
			GmailSender gs = new GmailSender();
			gs.sender("博文亭邮箱授权", "<html><head><meta http-equiv='Content-Type' content='text/html; charset=gb2312' /><title>hello world</title></head><body><p>博文亭恭喜您！收到邮件了！请点击授权链接进行授权</p><p><a href='"+url+"' target='_blank'>授权</a> </p></body></html>",email);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
