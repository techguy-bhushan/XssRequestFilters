package com.xss.filters.service;

/**
 * RansackXss service is responsible for remove xss from targeted request. You can create your own
 * custom implementation of it.
 *
 * @author Bhushan Uniyal
 */
public interface RansackXss {

  String ransack(String value);

}
