package com.xss.filter.config.filter;

import com.xss.filter.annotation.XssFilter;
import com.xss.filter.config.httpwrapper.CaptureRequestWrapper;
import com.xss.filter.service.RansackXssService;
import com.xss.filter.service.ServletRequestXssFilterManager;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;


/**
 * <p>This filter will only work for request which action have annotated {@link XssFilter}
 *
 * @author Bhushan Uniyal.
 */
public class CustomXssFilter implements Filter {

    private final RansackXssService ransackXss;
    private final ServletRequestXssFilterManager servletRequestXssFilterManager;

    public CustomXssFilter(RansackXssService ransackXssService,
                           ServletRequestXssFilterManager servletRequestXssFilterManager) {
        this.ransackXss = ransackXssService;
        this.servletRequestXssFilterManager = servletRequestXssFilterManager;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(new CaptureRequestWrapper((HttpServletRequest) servletRequest, ransackXss,
                servletRequestXssFilterManager),
            servletResponse);
    }

}

