package com.xss.request.filters.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xss.request.filters.annotation.XxsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/*
* @author  Bhushan Uniyal.
* This Component will search all the url's which action are annotated with @XxsFilter (collect the list of urls, which will be pick by
* CustomXssFilter)
* */
@Component
@DependsOn("requestMappingHandlerMapping")
public class XssFiltersConfiguration {
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    public List<String> xssMatches() {
        List<String> urls = new ArrayList<String >();
        Map<RequestMappingInfo, HandlerMethod> handlerMethods =
                this.requestMappingHandlerMapping.getHandlerMethods();

        for(Map.Entry<RequestMappingInfo, HandlerMethod> item : handlerMethods.entrySet()) {
            RequestMappingInfo mapping = item.getKey();
            HandlerMethod method = item.getValue();
            if (mapping.getPatternsCondition() != null && mapping.getPatternsCondition().getPatterns() != null) {
                for (String urlPattern : mapping.getPatternsCondition().getPatterns()) {
                    if(method.hasMethodAnnotation(XxsFilter.class)) {
                        urls.add(urlPattern);
                    }
                }
            }
        }
        return urls;
    }

}