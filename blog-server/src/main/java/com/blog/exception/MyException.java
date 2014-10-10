package com.blog.exception;

public class MyException extends Exception {

  private static final long serialVersionUID = 1L;

  private ErrorBean         errorBean;

  public MyException() {
  }

  public MyException(ErrorBean error) {
    setPrivilegeException(error.getError(), error);
  }

  public void setPrivilegeException(String message, ErrorBean errorBean) {
    System.out.println(message);
    this.errorBean = errorBean;
  }

  public ErrorBean getErrorBean() {
    return errorBean;
  }

  public void setErrorBean(ErrorBean errorBean) {
    this.errorBean = errorBean;
  }

}
