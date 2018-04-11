package com.xss.request.filters.service;

/*remove xss target from value.*/
public interface StripXss {
    String stripXSS(String value);
}
