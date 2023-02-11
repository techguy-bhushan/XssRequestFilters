package com.xss.filter.service.impl;

import com.xss.filter.config.XssFilterProperties;
import com.xss.filter.service.RansackXssService;
import com.xss.filter.service.ServletInputStreamXssFilterService;
import jakarta.servlet.ServletInputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Json Xss ServletInputStream Filter Implementation for ServletInputStream
 *
 * @author Bhushan Uniyal
 */

@Component
public class ServletInputStreamXssJsonFilterServiceImpl implements
    ServletInputStreamXssFilterService {

    private final XssFilterProperties xssFilterProperties;
    private final RansackXssService ransackXss;
    private final Log logger = LogFactory.getLog(this.getClass());

    public ServletInputStreamXssJsonFilterServiceImpl(XssFilterProperties xssFilterProperties, RansackXssService ransackXss) {
        this.xssFilterProperties = xssFilterProperties;
        this.ransackXss = ransackXss;
    }

    @Override
    public ServletInputStream getInputStream(ServletInputStream inputStream) {
        ServletInputStream servletInputStream = null;
        BufferedReader bufferedReader = null;
        try {
            if (inputStream != null) {
                StringBuilder stringBuilder = new StringBuilder();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
                servletInputStream = new XssServletInputStream(
                    new ByteArrayInputStream(
                        ransackXss.ransack(stringBuilder.toString()).getBytes()));
            }
        } catch (Exception e) {
            logger.error("Error occurred during parse request to json string ", e);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    logger.error("Error occurred during close bufferedReader", e);
                }
            }
        }
        return servletInputStream;
    }


    @Override
    public boolean isSupportedContentType(String contentType) {
        return xssFilterProperties.getSupportedJsonContentTypes().contains(contentType);
    }

}
