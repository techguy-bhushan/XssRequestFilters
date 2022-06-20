package com.xss.filters.service;

import static com.xss.filters.util.ConstantUtil.EMPTY;
import static com.xss.filters.util.ConstantUtil.FILTER_PATTERNS;

import java.util.regex.Pattern;

/**
 * Default implementation of {@link RansackXss}, You can override this implementation, create your
 * custom  implementation of RansackXss and mask as a bean
 *
 * @author Bhushan Uniyal.
 */
public class DefaultRansackXssImpl implements RansackXss {

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
