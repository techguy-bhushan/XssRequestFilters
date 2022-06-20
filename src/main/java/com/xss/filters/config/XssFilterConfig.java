package com.xss.filters.config;

import com.xss.filters.annotation.XssFilter;
import com.xss.filters.filter.CustomXssFilter;
import com.xss.filters.service.ServletInputStreamXssJsonFilterProvider;
import com.xss.filters.service.ServletInputStreamXssFilterProvider;
import com.xss.filters.service.ServletRequestXssFilterManager;
import com.xss.filters.service.DefaultRansackXssImpl;
import com.xss.filters.service.RansackXss;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * This component will register {@link CustomXssFilter} only if there will any {@link XssFilter}
 * annotation used in url mapping, if there will no @XxsFilte used in application then
 * CustomXssFilter will disable.
 *
 * @author Bhushan Uniyal.
 */

@Component
@DependsOn("xssFiltersConfiguration")
public class XssFilterConfig {

  private final XssFiltersConfiguration xssFiltersConfiguration;

  public XssFilterConfig(XssFiltersConfiguration xssFiltersConfiguration) {
    this.xssFiltersConfiguration = xssFiltersConfiguration;
  }

  @Bean
  public FilterRegistrationBean<CustomXssFilter> xssFilterRegistration(RansackXss ransackXss,
      ServletRequestXssFilterManager servletRequestXssFilterManager) {
    FilterRegistrationBean<CustomXssFilter> registration = new FilterRegistrationBean<>();
    registration.setFilter(new CustomXssFilter(ransackXss, servletRequestXssFilterManager));
    Set<String> patters = xssFiltersConfiguration.xssMatches();
    if (patters.size() > 0) {
      registration.addUrlPatterns(patters.toArray(new String[0]));
    } else {
      registration.setEnabled(false);
    }
    return registration;
  }

  @Bean
  @ConditionalOnMissingBean
  public RansackXss ransackXss() {
    return new DefaultRansackXssImpl();
  }

  @Bean
  @ConditionalOnMissingBean
  public ServletRequestXssFilterManager servletRequestXssManager(
      List<ServletInputStreamXssFilterProvider> servletInputStreamXssFilterProviders) {
    return new ServletRequestXssFilterManager(servletInputStreamXssFilterProviders);
  }

  @Bean("servletInputStreamXssProcessors")
  @ConditionalOnMissingBean
  public List<ServletInputStreamXssFilterProvider> servletInputStreamXssProcessors() {
    ArrayList<ServletInputStreamXssFilterProvider> servletInputStreamXssFilterProviders = new ArrayList<>();
    servletInputStreamXssFilterProviders.add(new ServletInputStreamXssJsonFilterProvider());
    return servletInputStreamXssFilterProviders;
  }


}
