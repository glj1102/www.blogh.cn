package com.blog.util;

import java.io.InputStream;
import java.util.Properties;

public class LogUtil {

  public static String info(String key) {
    Properties props = new Properties();
    try {
      InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("/config.properties");
      props.load(in);
      String value = props.getProperty(key);
      return value;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static String error(String key) {
    Properties props = new Properties();
    try {
      InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("/error.properties");
      props.load(in);
      String value = props.getProperty(key);
      return value;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

}