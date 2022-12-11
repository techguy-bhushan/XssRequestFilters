package com.xss.filters.service;

import com.xss.filters.httpwrapper.CaptureRequestWrapper;
import jakarta.servlet.ServletInputStream;

/**
 * ServletInputStreamXssFilterProvider to get ServletInputStream based on supported content type
 *
 * @author Bhushan Uniyal
 * */
public interface ServletInputStreamXssFilterProvider {

   ServletInputStream getInputStream(CaptureRequestWrapper captureRequestWrapper,
      RansackXss ransackXss);

  boolean isSupportedContentType(String contentType);

}
