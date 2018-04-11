package com.xss.request.filters.http.wrappers;

import com.xss.request.filters.service.StripXss;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


/**
 * @author  Bhushan Uniyal.
 * This class is responsible for filter the XSS in request you can add or remove the XSS handling logic in #stripXSS method in
 * CaptureRequestWrapper, CustomXssFilter use this class for remove xss in request.
 */
public class CaptureRequestWrapper extends HttpServletRequestWrapper {

    private StripXss stripXss;

    public CaptureRequestWrapper(HttpServletRequest servletRequest, StripXss stripXss) {
        super(servletRequest);
        this.stripXss = stripXss;
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
            encodedValues[i] = stripXss.stripXSS(values[i]);
        }
        return encodedValues;
    }

    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        return stripXss.stripXSS(value);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return stripXss.stripXSS(value);
    }


}