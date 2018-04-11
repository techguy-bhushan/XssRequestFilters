package com.xss.request.filters.config;

import com.xss.request.filters.filter.CustomXssFilter;
import com.xss.request.filters.service.DefaultStripXssImpl;
import com.xss.request.filters.service.StripXss;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.List;

/*
* @author Bhushan Uniyal.
*
* This component will register {@link com.xss.request.filters.filter.CustomXssFilter} only if there will any
* {@link com.xss.request.filters.annotation.XxsFilter}  annotation used in url mapping, if there will no @XxsFilte
* used in application then CustomXssFilter will disable.
* */
@Component
@DependsOn("xssFiltersConfiguration")
public class FilterConfig {
    @Autowired
    private XssFiltersConfiguration xssFiltersConfiguration;

    @Bean
    public FilterRegistrationBean xssFilterRegistration(StripXss stripXss) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CustomXssFilter(stripXss));
        List<String> patters = xssFiltersConfiguration.xssMatches();
        if(patters.size() > 0) {
            registration.addUrlPatterns((String[]) patters.toArray());
        } else {
            registration.setEnabled(false);
        }
        return registration;
    }

    @Bean
    @ConditionalOnMissingBean(name = "stripXss")
    public StripXss stripXss() {
        return new DefaultStripXssImpl();
    }
}
