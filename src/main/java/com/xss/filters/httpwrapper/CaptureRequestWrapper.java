package com.xss.filters.httpwrapper;

import com.xss.filters.service.RansackXss;
import com.xss.filters.service.ServletRequestXssFilterManager;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

/**
 * This class is responsible for filter the XSS in request you can add or remove the XSS handling
 * logic in #ransackXss method in CaptureRequestWrapper, CustomXssFilter use this class for remove
 * xss in request.
 *
 * @author Bhushan Uniyal.
 */
public class CaptureRequestWrapper extends HttpServletRequestWrapper {

    private final RansackXss ransackXss;
    private final ServletRequestXssFilterManager servletRequestXssFilterManager;


    public CaptureRequestWrapper(HttpServletRequest request, RansackXss ransackXss,
                                 ServletRequestXssFilterManager servletRequestXssFilterManager) {
        super(request);
        if (Objects.isNull(ransackXss)) {
            throw new IllegalArgumentException("ransackXss can't be null");
        }
        this.ransackXss = ransackXss;
        this.servletRequestXssFilterManager = servletRequestXssFilterManager;
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = ransackXss.ransack(values[i]);
        }
        return encodedValues;
    }

    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        return ransackXss.ransack(value);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return ransackXss.ransack(value);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return servletRequestXssFilterManager.getBufferedReader(this, ransackXss);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return servletRequestXssFilterManager.getServletInputStream(this, ransackXss);
    }

}
