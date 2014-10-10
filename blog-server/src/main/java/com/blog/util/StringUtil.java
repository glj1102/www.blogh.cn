package com.blog.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangpeng
 * @version 1.0 字符串方法
 *
 */
public class StringUtil {
 private static   Calendar c=null;
	public static void main(String[] args) {
//	  Calendar c = Calendar.getInstance();
//	  long i=c.getTimeInMillis();
//	  c.setTimeInMillis(i);
//	  c.add(Calendar.DATE, 365);
//	  Date date=c.getTime();
	}
	public static long getTime(long time,int day){

	  if(c==null){
	   c = Calendar.getInstance();

	  }
	  Date date=new Date(time);
	  c.setTime(date);
	  c.add(Calendar.DATE, day);
	  return c.getTimeInMillis();
	}

	public static long getTime() {
		String a = new Date().getTime() + "";
		String b = a.substring(0, 10);
		long l = Long.parseLong(a);
		return l;
	}



	/*
	 * 获取时间，从1970到现在的秒数
	 */
	public static long getTimestamp() {
		return new Date().getTime();

	}

	/*
	 * 获得时间。格式：yyyy-MM-dd HH:mm:ss
	 */
	public static String getStandTime() {
		DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dFormat.format(new Date());
	}

	/*
	 * 获得时间。格式：yyyy-MM-dd HH:mm:ss
	 */
	public static String getStandTime(Date date) {
		if (date == null) {
			return "";
		}
		DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dFormat.format(date);
	}

	/*
	 * 获得服务器的路径分隔符
	 */
	public static String getSeparator() {
		return File.separator;
	}

	/**
	 *
	 * @function: 反斜杠"\"转换为斜杠"/"
	 * @param backslash
	 *            包括反斜杠的字符串
	 * @return
	 * @author:
	 */
	public static String backslashToSlash(String backslash) {
		if (backslash == null) {
			return null;
		}
		return backslash.replace("\\", "/");
	}

	/*
	 * 获得当前的ActionPath名称
	 */
	public static String getActionPath(HttpServletRequest req) {
		String strPath = "";
		strPath = req.getRequestURI();
		int beginIndex = strPath.lastIndexOf("/");
		int endIndex = strPath.lastIndexOf(".");
		if (endIndex > beginIndex)
			// 包含.do
			strPath = strPath.substring(beginIndex + 1, endIndex);
		else
			// 不包含.do
			strPath = strPath.substring(beginIndex + 1, strPath.length());
		return strPath;
	}

	/**
	 * 当字符串为null，或者没有值是 返回true,否则返回false
	 *
	 * @function:判断一个字符串是否为空
	 * @param value
	 * @return true/false
	 */
	public static boolean isEmpty(String value) {
		if (value == null || value.trim().length() < 1) {
			return true;
		}
		return false;
	}

	public static String arrayToString(String[] s, String seperator) {
		StringBuilder returnValue = new StringBuilder();

		if (s != null && seperator != null) {
			for (int i = 0; i < s.length; i++) {
				returnValue.append(s[i]);
				returnValue.append(seperator);
			}
		}
		return returnValue.toString();
	}

	public static String[] stringToArray(String str, String flag) {
		return isEmpty(str) ? null : str.split(flag);
	}

	/**
	 * @function: 判断str是否包含在List<string>中
	 */
	public static boolean contains(List<String> lis, String str) {
		for (String item : lis) {
			if (str.equals(item)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @function: 把特殊字符替换成全角
	 */
	public static String escapeChar(String input) {
		if (input == null || input.length() == 0)
			return input;

		String regEx = ">|<|&|\"|'"; // 表示a或f
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(input);
		if (!m.find()) {
			return input;
		}

		StringBuffer buf = new StringBuffer(input.length() + 6);
		char ch = ' ';

		for (int i = 0; i < input.length(); i++) {
			ch = input.charAt(i);
			switch (ch) {
			case '>':
				buf.append("＞");
				break;
			case '<':
				buf.append("＜");
				break;
			case '&':
				buf.append("＆");
				break;
			case '"':
				buf.append("＂");
				break;
			case '\'':
				buf.append("＇");
				break;
			default:
				buf.append(ch);
			}

		}
		return buf.toString();
	}

	public static String MyHtmlEncode(String strContent) {
		strContent = strContent.replace("&", "＆");
		strContent = strContent.replace("'", "＇");
		strContent = strContent.replace("<", "＜");
		strContent = strContent.replace(">", "＞");
		strContent = strContent.replace("chr(60)", "＜");
		strContent = strContent.replace("chr(37)", "＞");
		strContent = strContent.replace("\"", "＂");
		strContent = strContent.replace(";", "；");
		strContent = strContent.replace("\r\n", "<br>");
		strContent = strContent.replace(" ", "　");
		return strContent;
	}

	/**
	 * @function: 过滤掉换行符号,用于textarea
	 * @return
	 * @author:
	 */
	public static String filterEnter(String src) {
		if (src == null) {
			return "";
		}

		String enter = String.valueOf((char) 13) + String.valueOf((char) 10);
		if (src.endsWith(enter)) {
			// 存在换行,过滤掉
			return src.substring(0, src.length() - enter.length());
		}
		return src;
	}

	/**
	 * @function: 得到当前系统时间序列
	 * @return 返回"yyMMddHHmmssSS"格式字符串
	 */
	public static String getTimeString() {
		DateFormat dFormat = new SimpleDateFormat("yyyyMMddHHmmssSS");
		return dFormat.format(new Date());
	}

	/**
	 * @function: 随机数
	 * @param length
	 * @return
	 */
	public static String getRandomChar(int length) {
		char[] chr = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
				'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
				'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
				'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
				'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
				'X', 'Y', 'Z' };

		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			buffer.append(chr[random.nextInt(62)]);
		}
		return buffer.toString();
	}

	public static String getRandomUppercase(int length) {
		char[] chr = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
				'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
				'X', 'Y', 'Z' };
		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			buffer.append(chr[random.nextInt(26)]);
		}
		return buffer.toString();
	}

	/**
	 * @function: 随机数字
	 * @param length
	 * @return
	 */
	public static String getRandomCharNum(int length) {
		char[] chr = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			buffer.append(chr[random.nextInt(10)]);
		}
		return buffer.toString();
	}

	/**
	 * @function: 生成10位随机数
	 * @return
	 */
	public static String getRandomChar() {
		return getRandomChar(10);
	}

	/**
	 * @function: 随机数(数字+小写字母)
	 * @param length
	 * @return
	 */
	public static String getRandomCharNumAndLowerLetters(int length) {
		char[] chr = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
				'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
				'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			buffer.append(chr[random.nextInt(36)]);
		}
		return buffer.toString();
	}

	/**
	 * 获得当前时间字符串.格式:yyMMddHHmmss.
	 *
	 * @author: zp8360@163.com
	 * @version: 2011-8-5 下午02:22:23
	 * @return
	 */
	public static String getDateTime() {
		DateFormat dFormat = new SimpleDateFormat("yyMMddHHmmss");
		return dFormat.format(new Date());
	}

	/**
	 * 票纸批次号.
	 *
	 * @author: zp8360@163.com
	 * @version: 2011-8-5 下午02:18:23
	 * @return
	 */
	public static String getSeqNumber() {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		num = (int) (random * 10000);
		return getDateTime() + num;
	}

	/**
	 * 生成四位随机数.
	 *
	 * @author: zp8360@163.com
	 * @version: 2012-2-2 下午07:47:10
	 * @return
	 */
	public static String getRandom4() {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		num = (int) (random * 10000);
		return String.valueOf(num);
	}

	/**
	 * 流水号(时间+6位随机). author:zp8360@163.com 2011-9-6 下午05:22:24
	 */
	public static String getSerialNumber() {
		String number = getDateTime();
		return number + getRandomCharNum(6);
	}

	/**
	 * 发送短信时的信息id(唯一).
	 *
	 * @author: zp8360@163.com
	 * @version: 2011-9-14 下午04:45:20
	 * @return
	 */
	public static Long getSmsSerialNumber() {
		return Long.valueOf(getSerialNumber());
	}

	/**
	 * 是否是合法手机号.
	 *
	 * @author: zp8360@163.com
	 * @version: 2011-9-28 上午09:24:11
	 * @param mobile
	 * @return
	 */
	public static boolean isMobile(String mobile) {
		String regExp = "^[1][3-8]\\d{9}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(mobile);
		return m.find();
	}

	/**
	 * author:zp8360@163.com 2011-11-19 下午04:15:22
	 *
	 * @param source
	 *            　原字符窜　abcdefg
	 * @param offer
	 *            　开始 2
	 * @param len
	 *            　长度 2
	 * @param cover
	 *            　覆盖字字符 *
	 * @return ab**efg
	 */
	public static String replace(String source, Integer offer, Integer len,
			String cover) {
		if (source.length() < offer)
			return source;
		String c = "";
		for (int i = 0; i < len; i++) {
			c += cover;
		}
		String s = source.substring(0, offer);
		if (source.length() < (offer + len)) {
			return (s + c).substring(0, source.length());
		}
		String e = source.substring(offer + len, source.length());
		return s + c + e;
	}

	/**
	 * 充值卡批次号(11位)
	 *
	 * @author zhangpeng
	 * @return
	 */
	public static String getBatchNum() {
		DateFormat dFormat = new SimpleDateFormat("yyyyMMdd");
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		num = (int) (random * 1000);
		return dFormat.format(new Date()) + num;
	}

	/**
	 * 充值卡卡号(19位)
	 *
	 * @author zhangpeng
	 * @return
	 */
	public static String getSn() {

		return getRandomCharNum(19);
	}

	/**
	 * 获取分表的表名
	 *
	 * @param sn
	 * @return
	 */
	public static String getTableName(String sn) {

		return sn.substring(0, 11);
	}

	/**
	 * 根据Sn获取批次号
	 *
	 * @param sn
	 * @return
	 */
	public static String getBatchNumBySn(String sn) {
		return sn.substring(0, 11);
	}



}
