package com.xss.filter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "xss.filter")
public class XssFilterProperties {
    private List<String> supportedJsonContentTypes;

    public List<String> getSupportedJsonContentTypes() {
        return supportedJsonContentTypes;
    }

    public void setSupportedJsonContentTypes(List<String> supportedJsonContentTypes) {
        this.supportedJsonContentTypes = supportedJsonContentTypes;
    }
}
