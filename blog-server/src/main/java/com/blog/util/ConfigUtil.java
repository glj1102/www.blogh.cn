package com.blog.util;

/**
 *
 */
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {

  public static String readValue(String key) {
    Properties props = new Properties();
    try {

      InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");
      props.load(in);
      String value = props.getProperty(key);
      return value;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

}
