package com.xss.request.filters.filter;

import com.xss.request.filters.http.wrappers.CaptureRequestWrapper;
import com.xss.request.filters.service.RansackXss;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


/**
 * @author  Bhushan Uniyal.
 *
 * This filter will only work for request which action have annotated {@link com.xss.request.filters.annotation.XxsFilter} (with help of   {@link com.xss.request.filters.config.XssFiltersConfiguration} )
 */
public class CustomXssFilter implements Filter {
    private RansackXss ransackXss;

    public CustomXssFilter(RansackXss ransackXss) {
        if (ransackXss == null) {
            throw new IllegalArgumentException("ransackXss can't be null");
        }
        this.ransackXss = ransackXss;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(new CaptureRequestWrapper((HttpServletRequest)servletRequest, ransackXss), servletResponse);
    }

    @Override
    public void destroy() {

    }
}
