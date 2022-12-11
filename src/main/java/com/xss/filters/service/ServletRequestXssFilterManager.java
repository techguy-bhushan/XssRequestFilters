package com.xss.filters.service;

import com.xss.filters.httpwrapper.CaptureRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import jakarta.servlet.ServletInputStream;

/**
 * ServletRequestXssFilterManager will process ServletInputStream and BufferedReader for CaptureRequestWrapper
 * based on ServletInputStreamXssProviders.
 *
 * @author Bhushan Uniyal
 * */
public class ServletRequestXssFilterManager {

  private final List<ServletInputStreamXssFilterProvider> servletInputStreamXssFilterProviders;

  public ServletRequestXssFilterManager(
      List<ServletInputStreamXssFilterProvider> servletInputStreamXssFilterProviders) {
    this.servletInputStreamXssFilterProviders = servletInputStreamXssFilterProviders;
  }

  public ServletInputStream getServletInputStream(CaptureRequestWrapper captureRequestWrapper,
      RansackXss ransackXss)
      throws IOException {
    ServletInputStream servletInputStream = null;
    if (servletInputStreamXssFilterProviders != null) {
      for (ServletInputStreamXssFilterProvider servletInputStreamXssFilterProvider : servletInputStreamXssFilterProviders) {
        if (servletInputStreamXssFilterProvider != null && servletInputStreamXssFilterProvider.isSupportedContentType(
            captureRequestWrapper.getContentType())) {
          servletInputStream = servletInputStreamXssFilterProvider.getInputStream(captureRequestWrapper,
              ransackXss);
        }
      }
    }
    return servletInputStream != null ? servletInputStream
        : captureRequestWrapper.getRequest().getInputStream();
  }

  public BufferedReader getBufferedReader(CaptureRequestWrapper captureRequestWrapper,
      RansackXss ransackXss)
      throws IOException {
    BufferedReader bufferedReader = null;
    if (servletInputStreamXssFilterProviders != null) {
      for (ServletInputStreamXssFilterProvider servletInputStreamXssFilterProvider : servletInputStreamXssFilterProviders) {
        if (servletInputStreamXssFilterProvider != null && servletInputStreamXssFilterProvider.isSupportedContentType(
            captureRequestWrapper.getContentType())) {
          bufferedReader = new BufferedReader(
              new InputStreamReader(this.getServletInputStream(captureRequestWrapper, ransackXss)));
        }
      }
    }
    return bufferedReader == null ? captureRequestWrapper.getRequest().getReader() : bufferedReader;
  }

}
