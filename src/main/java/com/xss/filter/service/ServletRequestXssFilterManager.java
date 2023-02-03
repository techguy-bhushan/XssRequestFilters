package com.xss.filter.service;

import jakarta.servlet.ServletInputStream;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * ServletRequestXssFilterManager will process ServletInputStream and BufferedReader for CaptureRequestWrapper
 * based on ServletInputStreamXssProviders.
 *
 * @author Bhushan Uniyal
 */
public class ServletRequestXssFilterManager {

    private final List<ServletInputStreamXssFilterService> servletInputStreamXssFilterServices;

    public ServletRequestXssFilterManager(
        List<ServletInputStreamXssFilterService> servletInputStreamXssFilterServices) {
        this.servletInputStreamXssFilterServices = Optional.ofNullable(servletInputStreamXssFilterServices).orElse(new ArrayList<>());
    }

    public ServletInputStream getServletInputStream(ServletInputStream requestServletInputStream, String contentType) {
        return servletInputStreamXssFilterServices.stream()
            .filter(Objects::nonNull)
            .filter(filter -> filter.isSupportedContentType(contentType))
            .findAny()
            .map(filter -> filter.getInputStream(requestServletInputStream))
            .orElse(requestServletInputStream);
    }

    public BufferedReader getBufferedReader(ServletInputStream servletInputStream, String contentType, BufferedReader requestBufferedReader) {
        return servletInputStreamXssFilterServices.stream()
            .filter(Objects::nonNull)
            .filter(filter -> filter.isSupportedContentType(contentType))
            .findAny()
            .map(filter -> new BufferedReader(new InputStreamReader(this.getServletInputStream(servletInputStream, contentType))))
            .orElse(requestBufferedReader);

    }

}
