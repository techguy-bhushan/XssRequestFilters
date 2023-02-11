package com.xss.filter.service.impl;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * XssServletInputStream read data using ByteArrayInputStream.
 *
 * @author Bhushan
 */
public class XssServletInputStream extends ServletInputStream {

    private final ByteArrayInputStream requestBody;

    public XssServletInputStream(final ByteArrayInputStream requestBody) {
        this.requestBody = requestBody;
    }

    @Override
    public boolean isFinished() {
        try {
            return requestBody.available() == 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {

    }

    @Override
    public int read() throws IOException {
        return this.requestBody.read();
    }
}
