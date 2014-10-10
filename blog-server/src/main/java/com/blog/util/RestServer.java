package com.blog.util;

import java.lang.reflect.Field;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.blog.exception.ErrorBean;
import com.blog.exception.MyException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

/**
 * Rest请求处理类 针对Jersey框架提供的RestAPI接口调用的封装
 *
 * @author zp
 * @version 0.0.3 1.添加处理返回状态不是200的错误 2.完善异常的处理
 *
 */
public class RestServer {

  public static Logger log = Logger.getLogger(RestServer.class);

  /**
   * 使用本类需要了解的概念 1. 基础地址变量： |http://127.0.0.1/system_context | /getinfo |
   * /name/a.json | |系统部署地址 | 类Path | 方法Path | |Rest服务器地址 | RestAPI接口 |
   * RestAPI接口地址 | |server | restURI | path |
   */
  public static Client c   = null;

  public static Client getClient() {
    if (c == null) {
      ClientConfig config = new DefaultClientConfig();
      config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
      c = Client.create(config);
    }
    return c;
  }

  /**
   * 创建JerseyClient实例
   *
   * @param server
   *          Rest服务器地址
   * @param restURI
   *          RestAPI接口
   * @return webResource
   */
  public static WebResource getWebResource(String server, String restURI) {
    WebResource webResource = getClient().resource(server + restURI);
    return webResource;
  }

  /**
   * Post请求的处理
   *
   * @param server
   *          Rest服务器地址
   * @param restURI
   *          RestAPI接口
   * @param path
   *          RestAPI接口地址
   * @param object
   *          需要post提交的对象（传入实体本类型）
   * @return 服务器返回内容
   * @throws MyException
   *           截获此方法的所有异常，并统一throw PrivilegeException
   *
   */
  public static String postRest(String server, String restURI, String path, Object object) throws MyException {

    String str = "";
    ClientResponse response = null;
    try {
      log.info("[request url:]" + server + restURI + "/" + path);
      response = getWebResource(server, restURI).path(path).header("Content-Type", MediaType.APPLICATION_JSON)
          .type(MediaType.APPLICATION_JSON).post(ClientResponse.class, object);
      str = response.getEntity(String.class);
    } catch (Throwable t) {
      log.info("[post error:]", t);
      ErrorBean eb = new ErrorBean();
      eb.setRequest(server + restURI + path);
      eb.setErrorCode("NetWorkError");
      eb.setError("[ postRest method throw ClientHandlerException ]");
      throw new MyException(eb);
    }

    if (response.getStatus() != 200) {
      log.info("[post getEntity Value:]" + str);
      ErrorBean eb = new ErrorBean();
      eb.setRequest(server + restURI + path);
      eb.setErrorCode("Not200Error");
      eb.setError("[ postRest method throw " + response.getStatus() + " Exception ]");
      throw new MyException(eb);
    }

    log.info("[post getEntity Value:]" + str);
    return str;

  }

  /**
   * Put请求的处理
   *
   * @param server
   *          Rest服务器地址
   * @param restURI
   *          RestAPI接口
   * @param path
   *          RestAPI接口地址
   * @param object
   *          需要put提交的对象（传入实体本类型）
   * @return 服务器返回内容
   * @throws MyException
   *           截获此方法的所有异常，并统一throw PrivilegeException
   *
   */
  public static String putRest(String server, String restURI, String path, Object object) throws MyException {

    String str = "";
    ClientResponse response = null;
    try {
      log.info("[request url:]" + server + restURI + "/" + path);
      response = getWebResource(server, restURI).path(path).header("Content-Type", MediaType.APPLICATION_JSON)
          .type(MediaType.APPLICATION_JSON).put(ClientResponse.class, object);
      str = response.getEntity(String.class);
      log.info("[put getEntity Value:]" + str);

    } catch (Throwable t) {
      log.info("[put error:]", t);
      ErrorBean eb = new ErrorBean();
      eb.setRequest(server + restURI + path);
      eb.setErrorCode("NetWorkError");
      eb.setError("[ putRest method throw ClientHandlerException ]");
      throw new MyException(eb);
    }

    if (response.getStatus() != 200) {
      log.info("[put getEntity Value:]" + str);
      ErrorBean eb = new ErrorBean();
      eb.setRequest(server + restURI + path);
      eb.setErrorCode("Not200Error");
      eb.setError("[ putRest method throw " + response.getStatus() + " Exception ]");
      throw new MyException(eb);
    }

    return str;
  }

  /**
   * DELETE请求的处理
   *
   * @param server
   *          Rest服务器地址
   * @param restURI
   *          RestAPI接口
   * @param path
   *          RestAPI接口地址
   * @return 服务器返回内容
   * @throws MyException
   *           截获此方法的所有异常，并统一throw PrivilegeException
   */
  public static String deleteRest(String server, String restURI, String path) throws MyException {

    String str = "";
    ClientResponse response = null;
    try {
      log.info("[request url:]" + server + restURI + "/" + path);
      response = getWebResource(server, restURI).path(path).header("Content-Type", MediaType.APPLICATION_JSON)
          .type(MediaType.APPLICATION_JSON).delete(ClientResponse.class);
      str = response.getEntity(String.class);
      log.info("[delete getEntity Value:]" + str);
      System.out.println("[delete getEntity Value:]" + str);

    } catch (Throwable t) {
      log.info("[delete error:]", t);
      ErrorBean eb = new ErrorBean();
      eb.setRequest(server + restURI + path);
      eb.setErrorCode("NetWorkError");
      eb.setError("[ deleteRest method throw ClientHandlerException ]");
      throw new MyException(eb);
    }

    if (response.getStatus() != 200) {
      log.info("[put getEntity Value:]" + str);
      ErrorBean eb = new ErrorBean();
      eb.setRequest(server + restURI + path);
      eb.setErrorCode("Not200Error");
      eb.setError("[ putRest method throw " + response.getStatus() + " Exception ]");
      throw new MyException(eb);
    }

    return str;
  }

  /**
   * GET请求的处理
   *
   * @param server
   *          Rest服务器地址
   * @param restURI
   *          RestAPI接口
   * @param path
   *          RestAPI接口地址
   * @return 服务器返回内容
   * @throws MyException
   *           截获此方法的所有异常，并统一throw PrivilegeException
   */
  public static String getRest(String server, String restURI, String path,String name,String value ) throws MyException {

    String str = null;
    ClientResponse response = null;
    try {
      log.info("[request url:]" + server + restURI + "/" + path);
      if(name!=null)
      response = getWebResource(server, restURI).path(path).header("Content-Type", MediaType.APPLICATION_JSON)
          .type(MediaType.APPLICATION_JSON).header(name, value).get(ClientResponse.class);
      else
        response = getWebResource(server, restURI).path(path).header("Content-Type", MediaType.APPLICATION_JSON)
        .type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
      str = response.getEntity(String.class);
      log.info("[get getEntity Value:]" + str);
      System.out.println("[get getEntity Value:]" + str);

    } catch (Throwable t) {
      log.info("[get error:]", t);
      ErrorBean eb = new ErrorBean();
      eb.setRequest(server + restURI + path);
      eb.setErrorCode("NetWorkError");
      eb.setError("[ getRest method throw ClientHandlerException ]");
      throw new MyException(eb);
    }

    if (response.getStatus() != 200) {
      log.info("[get getEntity Value:]" + str);
      ErrorBean eb = new ErrorBean();
      eb.setRequest(server + restURI + path);
      eb.setErrorCode("Not200Error");
      eb.setError("[ getRest method throw " + response.getStatus() + " Exception ]");
      throw new MyException(eb);
    }
    return str;

  }

  /**
   * 将服务器返回内容转换成对象
   *
   * @param str
   * @param clazz
   * @return
   */

  public static <T> T toBean(String str, Class<T> clazz) throws MyException {

    ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
    boolean convertFlag = false;
    T obj = null;
    ErrorBean error = null;
    try {
      Field[] fields = ErrorBean.class.getDeclaredFields();
      for (Field field : fields) {
        if (str.contains(field.getName())) {
          convertFlag = true;
        }
      }
      if (convertFlag) {
        error = mapper.readValue(str.getBytes("utf-8"), ErrorBean.class);
        log.info("[convert error]" + mapper.writeValueAsString(error));
        System.out.println("[convert error]" + mapper.writeValueAsString(error));
        throw new MyException(error);
      } else {
        obj = mapper.readValue(str.getBytes("utf-8"), clazz);
      }

    } catch (MyException e) {
      try {
        log.info("[PrivilegeException]" + mapper.writeValueAsString(e.getErrorBean()).toString());

      } catch (Exception e1) {
        e1.printStackTrace();
      }
      throw e;
    } catch (Throwable e) {
      e.printStackTrace();
      ErrorBean eb = new ErrorBean();
      eb.setRequest("toBean");
      eb.setErrorCode("ToBeanError");
      eb.setError("[ toBean method throw ConvertException ],str=" + str + " , class=" + clazz);
      throw new MyException(eb);
    }

    return obj;
  }

}