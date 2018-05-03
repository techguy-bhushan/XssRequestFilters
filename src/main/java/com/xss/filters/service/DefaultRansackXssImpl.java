package com.xss.filters.service;

import java.util.regex.Pattern;

import static com.xss.filters.util.ConstantUtil.*;

/**
 * Default implementation of {@link RansackXss}, You can override this implementation, create your custom  implementation of RansackXss and mask as a bean
 *
 * @author  Bhushan Uniyal.
 */
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
