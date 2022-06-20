package com.xss.filters.config;

import com.xss.filters.annotation.XssFilter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

/**
 * This Component will search all the url's which action are annotated with @XssFilter (collect the
 * list of urls, which will be pick by CustomXssFilter)
 *
 * @author Bhushan Uniyal.
 */
@Component
@DependsOn("requestMappingHandlerMapping")
public class XssFiltersConfiguration {

  private final RequestMappingHandlerMapping requestMappingHandlerMapping;

  public XssFiltersConfiguration(RequestMappingHandlerMapping requestMappingHandlerMapping) {
    this.requestMappingHandlerMapping = requestMappingHandlerMapping;
  }

  public Set<String> xssMatches() {
    Set<String> urls = new HashSet<>();
    Map<RequestMappingInfo, HandlerMethod> handlerMethods =
        this.requestMappingHandlerMapping.getHandlerMethods();

    for (Map.Entry<RequestMappingInfo, HandlerMethod> item : handlerMethods.entrySet()) {
      RequestMappingInfo mapping = item.getKey();
      HandlerMethod method = item.getValue();
      if (mapping.getPatternsCondition() != null
          && mapping.getPatternsCondition().getPatterns() != null) {
        for (String urlPattern : mapping.getPatternsCondition().getPatterns()) {
          addUrlPatternIfMethodAnnotatedWithXssFilter(urls, method, urlPattern);
        }
      }
      if (mapping.getPathPatternsCondition() != null
          && mapping.getPathPatternsCondition().getPatterns() != null) {
        for (PathPattern urlPattern : mapping.getPathPatternsCondition().getPatterns()) {
          addUrlPatternIfMethodAnnotatedWithXssFilter(urls, method, urlPattern.getPatternString());
        }
      }
    }
    return urls;
  }

  private void addUrlPatternIfMethodAnnotatedWithXssFilter(Set<String> urls, HandlerMethod method,
      String urlPattern) {
    if (method.hasMethodAnnotation(XssFilter.class)) {
      urls.add(urlPattern);
    }
  }

}
