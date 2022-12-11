package com.xss.filters.filter;

import com.xss.filters.annotation.XssFilter;
import com.xss.filters.config.XssFiltersConfiguration;
import com.xss.filters.httpwrapper.CaptureRequestWrapper;
import com.xss.filters.service.RansackXss;
import com.xss.filters.service.ServletRequestXssFilterManager;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;



/**
 * <p>This filter will only work for request which action have annotated {@link XssFilter} (with
 * help of   {@link XssFiltersConfiguration})</p>
 *
 * @author Bhushan Uniyal.
 */
public class CustomXssFilter implements Filter {

  private final RansackXss ransackXss;
  private final ServletRequestXssFilterManager servletRequestXssFilterManager;

  public CustomXssFilter(RansackXss ransackXss,
      ServletRequestXssFilterManager servletRequestXssFilterManager) {
    this.ransackXss = ransackXss;
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
