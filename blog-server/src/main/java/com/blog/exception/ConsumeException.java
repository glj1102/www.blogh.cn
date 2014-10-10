package com.blog.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * 
 * @author zp
 */
public class ConsumeException extends WebApplicationException {

  private static final long serialVersionUID = 1L;

  public ConsumeException(ErrorBean error) {
    super(Response.serverError().entity(error).status(200).build());
  }

  public static void error(ErrorBean eb) {
    throw new ConsumeException(eb);
  }

  public static void error(String errorCode) {
    ErrorBean errorBean = new ErrorBean();
    errorBean.setErrorCode(errorCode);
    String errorValue = ErrorUtil.readValue(errorCode);
    errorBean.setError(errorValue);
    throw new ConsumeException(errorBean);
  }

  public static void error(String errorCode, String request) {
    ErrorBean errorBean = new ErrorBean();
    errorBean.setErrorCode(errorCode);
    String errorValue = ErrorUtil.readValue(errorCode);
    errorBean.setError(errorValue);
    throw new ConsumeException(errorBean);
  }

}