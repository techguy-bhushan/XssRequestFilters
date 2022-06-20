package com.xss.filters.service;

import java.io.ByteArrayInputStream;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

/**
 * XssServletInputStream read data using ByteArrayInputStream.
 *
 * @author Bhushan
 */
public class XssServletInputStream extends ServletInputStream {

  private final ByteArrayInputStream requestBody;

  public XssServletInputStream(ByteArrayInputStream requestBody) {
    this.requestBody = requestBody;
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public boolean isReady() {
    return true;
  }

  @Override
  public void setReadListener(ReadListener readListener) {

  }

  @Override
  public int read() {
    return requestBody.read();
  }

}
