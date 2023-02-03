package com.xss.filter.config;

import com.xss.filter.annotation.XssFilter;
import com.xss.filter.config.filter.CustomXssFilter;
import com.xss.filter.service.RansackXssService;
import com.xss.filter.service.ServletInputStreamXssFilterService;
import com.xss.filter.service.ServletRequestXssFilterManager;
import com.xss.filter.service.impl.DefaultRansackXssServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import java.util.*;

/**
 * This component will register {@link CustomXssFilter} only if there will any {@link XssFilter}
 * annotation used in url mapping, if there will no @XxsFilte used in application then
 * CustomXssFilter will disable.
 *
 * @author Bhushan Uniyal.
 */

@Component
@DependsOn("requestMappingHandlerMapping")
public class XssFilterConfig {

    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    public XssFilterConfig(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    }

    @Bean
    public FilterRegistrationBean<CustomXssFilter> xssFilterRegistration(RansackXssService ransackXssService,
                                                                         ServletRequestXssFilterManager servletRequestXssFilterManager) {
        FilterRegistrationBean<CustomXssFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new CustomXssFilter(ransackXssService, servletRequestXssFilterManager));
        Set<String> patters = getUrlPatterns();
        if (patters.size() > 0) {
            registration.addUrlPatterns(patters.toArray(new String[0]));
        } else {
            registration.setEnabled(false);
        }
        return registration;
    }

    @Bean
    @ConditionalOnMissingBean
    public RansackXssService ransackXss() {
        return new DefaultRansackXssServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public ServletRequestXssFilterManager servletRequestXssManager(
        List<ServletInputStreamXssFilterService> servletInputStreamXssFilterServices) {
        return new ServletRequestXssFilterManager(servletInputStreamXssFilterServices);
    }

    @Bean("servletInputStreamXssProcessors")
    @ConditionalOnMissingBean
    public List<ServletInputStreamXssFilterService> servletInputStreamXssProcessors(ServletInputStreamXssFilterService servletInputStreamXssJsonFilterServiceImpl) {
        ArrayList<ServletInputStreamXssFilterService> servletInputStreamXssFilterServices = new ArrayList<>();
        servletInputStreamXssFilterServices.add((servletInputStreamXssJsonFilterServiceImpl));
        return servletInputStreamXssFilterServices;
    }


    private Set<String> getUrlPatterns() {
        final Set<String> urls = new HashSet<>();
        this.requestMappingHandlerMapping.getHandlerMethods()
            .forEach((mapping, method) -> {
                addPatternsRequestConditionUrls(urls, mapping, method);
                addPathPatternsConditionUrls(urls, mapping, method);
            });
        return urls;
    }

    private void addPathPatternsConditionUrls(Set<String> urls, RequestMappingInfo mapping, HandlerMethod method) {
        Optional.ofNullable(mapping.getPathPatternsCondition())
            .map(PathPatternsRequestCondition::getPatterns)
            .ifPresent(urlPatterns -> urlPatterns
                .stream()
                .filter(Objects::nonNull)
                .map(PathPattern::getPatternString)
                .forEach(urlPattern -> addUrlPatternIfMethodAnnotatedWithXssFilter(urls, method, urlPattern)));
    }

    private void addPatternsRequestConditionUrls(Set<String> urls, RequestMappingInfo mapping, HandlerMethod method) {
        Optional.ofNullable(mapping.getPatternsCondition())
            .map(PatternsRequestCondition::getPatterns)
            .ifPresent(urlPatterns -> urlPatterns.forEach(urlPattern -> addUrlPatternIfMethodAnnotatedWithXssFilter(urls, method, urlPattern)));
    }


    private void addUrlPatternIfMethodAnnotatedWithXssFilter(Set<String> urls, HandlerMethod method,
                                                             String urlPattern) {
        if (method.hasMethodAnnotation(XssFilter.class)) {
            urls.add(urlPattern);
        }
    }

}
