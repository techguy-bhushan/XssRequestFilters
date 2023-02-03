package com.xss.filter.service;

import jakarta.servlet.ServletInputStream;

/**
 * ServletInputStreamXssFilterProvider to get ServletInputStream based on supported content type
 *
 * @author Bhushan Uniyal
 */
public interface ServletInputStreamXssFilterService {

    ServletInputStream getInputStream(ServletInputStream servletInputStream);

    boolean isSupportedContentType(String contentType);

}
