package com.xss.request.filters.service;

import java.util.regex.Pattern;

import static com.xss.request.filters.util.ConstantUtil.*;

public class DefaultRansackXssImpl implements RansackXss {
    @Override
    public String ransackXss(String value) {
        if (value != null) {
           for (Pattern pattern : FILTER_PATTERNS) {
               value = pattern.matcher(value).replaceAll(EMPTY);
           }
        }
        return value;
    }
}
