package com.xss.filters.filter;

import com.xss.filters.annotation.XssFilter;
import com.xss.filters.config.XssFiltersConfiguration;
import com.xss.filters.httpwrapper.CaptureRequestWrapper;
import com.xss.filters.service.ServletRequestXssFilterManager;
import com.xss.filters.service.RansackXss;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


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
