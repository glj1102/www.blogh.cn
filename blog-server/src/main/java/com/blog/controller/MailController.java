package com.blog.controller;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import com.avaje.ebean.Ebean;
import com.blog.accept.bean.ResultBean;
import com.blog.models.UserMail;
import com.blog.service.MailService;
import com.blog.util.DES;
import com.blog.vo.EmailVo;

@Path("/mail")
public class MailController {

	public MailService mailService=null;

	@POST
	@Path("/create.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResultBean add(UserMail user){
		try {
			mailService = MailService.getInstance();
			DES des = new DES();
			UserMail usermail = mailService.get(des.decrypt(user.getEmail()));
			if(usermail==null){
				user.setEmail(des.decrypt(user.getEmail()));
				user.setStatus("normal");
				user.setCreate_time(new Date());
				Ebean.save(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResultBean("ok");
	}

	@GET
	@Path("/check.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResultBean Check(@Context HttpHeaders headers){
		ResultBean resultBean=null;
		String email = headers.getRequestHeader("email").get(0);
		mailService = MailService.getInstance();
		UserMail usermail = mailService.get(email);
		if(usermail==null){
			resultBean = new ResultBean("not");
		}else if("normal".equals(usermail.getStatus())){
			resultBean = new ResultBean("normal");
		}else if("cancel".equals(usermail.getStatus())){
			resultBean = new ResultBean("cancel");
		}
		return resultBean;
	}

	/**
	 * 修改邮箱状态
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/change.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResultBean change(@Context HttpHeaders headers){
		long id=Long.parseLong(headers.getRequestHeader("id").get(0));
		mailService = MailService.getInstance();
		mailService.change(id);
		return new ResultBean("ok");
	}


	@GET
	@Path("/list.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public EmailVo list(@Context HttpHeaders headers){
		String email=headers.getRequestHeader("email").get(0);
		int curpage = Integer.parseInt(headers.getRequestHeader("curpage").get(0));
		int pagesize = Integer.parseInt(headers.getRequestHeader("pagesize").get(0));
		mailService = MailService.getInstance();
		EmailVo ev = mailService.list(email,curpage,pagesize);
		return ev;
	}
	/**
	 * 发送邮件
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/send.json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResultBean send(@Context HttpHeaders headers){
		String email=headers.getRequestHeader("email").get(0);
		mailService = MailService.getInstance();
		mailService.send(email);
		return new ResultBean("ok");
	}
}
