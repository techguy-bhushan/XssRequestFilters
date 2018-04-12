package com.xss.request.filters.service;

/*remove xss target from value.*/
public interface RansackXss {
    String ransackXss(String value);
}
