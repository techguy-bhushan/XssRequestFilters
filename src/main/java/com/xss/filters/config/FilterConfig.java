package com.xss.filters.config;

import com.xss.filters.service.DefaultRansackXssImpl;
import com.xss.filters.service.RansackXss;
import com.xss.filters.filter.CustomXssFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This component will register {@link CustomXssFilter} only if there will any
 * {@link com.xss.filters.annotation.XxsFilter}  annotation used in url mapping, if there will no @XxsFilte
 * used in application then CustomXssFilter will disable.
 *
 * @author Bhushan Uniyal.
 * */

@Component
@DependsOn("xssFiltersConfiguration")
public class FilterConfig {

    @Autowired
    private XssFiltersConfiguration xssFiltersConfiguration;

    @Bean
    public FilterRegistrationBean xssFilterRegistration(RansackXss ransackXss) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CustomXssFilter(ransackXss));
        List<String> patters = xssFiltersConfiguration.xssMatches();
        if(patters.size() > 0) {
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
}
