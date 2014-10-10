<%@ page contentType="text/html; charset=UTF-8" language="java" import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.jspsmart.upload.SmartUpload" %>
<jsp:useBean id="mySmartUpload" scope="page" class="com.jspsmart.upload.SmartUpload" /> 
<HTML> 
<BODY > 
<% 
String webroot = request.getContextPath();

int maxsize=500;

int count=0; 
int filesize=0;
//随机数
Random r=new Random(System.currentTimeMillis());
int n=r.nextInt();
if(n<0){
	n=0-n;
}
//定义目标目录 

String uploadpath=application.getRealPath("/")+"front/fckimg/";
//FileUtil.createDir(uploadpath,true);
String strfilesize="";
mySmartUpload.initialize(pageContext);//文件上传 
mySmartUpload.upload(); //获得文本的内容 
String file_name=null;
com.jspsmart.upload.File myFile = mySmartUpload.getFiles().getFile(0); 
boolean isbool=true;
//限制图片不能大于500K
if (!myFile.isMissing())
{ 
	filesize=myFile.getSize();
	if(filesize>1024)
	{
		strfilesize=(filesize/1024)+"K";
	}
	if(filesize>1048576)
	{
		java.text.NumberFormat  formater  =  java.text.DecimalFormat.getInstance(); 
		formater.setMaximumFractionDigits(2); 
		formater.setMinimumFractionDigits(2); 
		strfilesize=formater.format((float)(filesize/1048576))+"M";
	}
	if(myFile.getSize()>1024*maxsize)
	{
		out.print("<script language='javascript'>alert('文件太大');history.back();</script>");
		isbool=false;
	}
	String temp=myFile.getFileName();
	temp=temp.substring(temp.indexOf(".")+1);
	if(temp.equalsIgnoreCase("jpeg")){
			temp=".jpg";
	}
	else if(temp.equalsIgnoreCase("jpg")){
			temp=".jpg";
	}
	else if(temp.equalsIgnoreCase("png")){
			temp=".png";
	}
	else if(temp.equalsIgnoreCase("gif")){
			temp=".gif";
	}
	else if(temp.equalsIgnoreCase("bmp")){
			temp=".bmp";
	}
	else if(temp.equalsIgnoreCase("swf")){
			temp=".swf";
	}
	else if(temp.equalsIgnoreCase("fla")){
			temp=".fla";
	}
	else if(temp.equalsIgnoreCase("mp3")){
			temp=".mp3";
	}
	else if(temp.equalsIgnoreCase("wmv")){
			temp=".wmv";
	}
	else if(temp.equalsIgnoreCase("rm")){
			temp=".rm";
	}
	else if(temp.equalsIgnoreCase("ram")){
			temp=".ram";
	}
	else if(temp.equalsIgnoreCase("rmvb")){
			temp=".rmvb";
	}
	else if(temp.equalsIgnoreCase("avi")){
			temp=".avi";
	}
	else if(temp.equalsIgnoreCase("wma")){
			temp=".wma";
	}
	//file upload
	/*
	else if(temp.equalsIgnoreCase("rar")){
			temp=".rar";
	}
	else if(temp.equalsIgnoreCase("zip")){
			temp=".zip";
	}
	else if(temp.equalsIgnoreCase("doc")){
			temp=".doc";
	}
	else if(temp.equalsIgnoreCase("txt")){
			temp=".txt";
	}	
	else if(temp.equalsIgnoreCase("xls")){
			temp=".xls";
	}
	*/
	//
	else if(temp.equalsIgnoreCase("jsp")){
			temp=".gif";
			out.print("<script language='javascript'>alert('Invalid file type(jpg,png,gif,bmp)');history.back();</script>");
			isbool=false;
	}
	else{
			temp=".gif";
			out.print("<script language='javascript'>alert('jpg,png,gif,bmp');history.back();</script>");
			isbool=false;
	}	
	file_name=n+temp;
	if(isbool)	{myFile.saveAs(uploadpath+file_name);}
	else{return ;}	
}
%> 
<script type="text/javascript">
	window.parent.OnUploadCompleted(0,"<%=webroot + "/front/fckimg/"+file_name%>","<%=file_name%>","") ;
</script>
</BODY> 
</HTML> 
