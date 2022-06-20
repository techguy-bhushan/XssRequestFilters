package com.xss.filters.service;

import com.xss.filters.httpwrapper.CaptureRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletInputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Json Xss ServletInputStream Filter Implementation for ServletInputStream
 *
 * @author Bhushan Uniyal
 * */
public class ServletInputStreamXssJsonFilterProvider implements
    ServletInputStreamXssFilterProvider {

  private final static List<String> SUPPORTED_JSON_CONTENT_TYPE = Arrays.asList(
      "application/json", "application/problem+json",
      "application/json;charset=UTF-8");
  private final Log logger = LogFactory.getLog(this.getClass());

  @Override
  public ServletInputStream getInputStream(CaptureRequestWrapper captureRequestWrapper,
      RansackXss ransackXss) {
    ServletInputStream servletInputStream = null;
    BufferedReader bufferedReader = null;
    try {
      InputStream inputStream = captureRequestWrapper.getRequest().getInputStream();
      if (inputStream != null) {
        StringBuilder stringBuilder = new StringBuilder();
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        char[] charBuffer = new char[128];
        int bytesRead;
        while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
          stringBuilder.append(charBuffer, 0, bytesRead);
        }
        servletInputStream = new XssServletInputStream(
            new ByteArrayInputStream(
                ransackXss.ransack(stringBuilder.toString()).getBytes()));
      }
    } catch (Exception e) {
      logger.error("Error occurred during parse request to json string ", e);
    } finally {
      if (bufferedReader != null) {
        try {
          bufferedReader.close();
        } catch (IOException e) {
          logger.error("Error occurred during close bufferedReader", e);
        }
      }
    }
    return servletInputStream;
  }


  @Override
  public boolean isSupportedContentType(String contentType) {
    return SUPPORTED_JSON_CONTENT_TYPE.contains(contentType);
  }

}
