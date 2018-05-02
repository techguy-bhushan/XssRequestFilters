package com.xss.filters.filter;

import com.xss.filters.annotation.XxsFilter;
import com.xss.filters.config.XssFiltersConfiguration;
import com.xss.filters.service.RansackXss;
import com.xss.filters.http.wrappers.CaptureRequestWrapper;

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
 * This filter will only work for request which action have annotated {@link XxsFilter} (with help of   {@link XssFiltersConfiguration} )
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
