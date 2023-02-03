package com.xss.filter.service.impl;

import com.xss.filter.service.RansackXssService;

import java.util.regex.Pattern;

import static com.xss.filter.util.ConstantUtil.EMPTY;
import static com.xss.filter.util.ConstantUtil.FILTER_PATTERNS;

/**
 * Default implementation of {@link RansackXssService}, You can override this implementation, create your
 * custom  implementation of RansackXss and mask as a bean
 *
 * @author Bhushan Uniyal.
 */
public class DefaultRansackXssServiceImpl implements RansackXssService {

    @Override
    public String ransack(String value) {
        if (value != null) {
            for (Pattern pattern : FILTER_PATTERNS) {
                value = pattern.matcher(value).replaceAll(EMPTY);
            }
        }
        return value;
    }

}
