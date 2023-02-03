package com.xss.filter.annotation;

import java.lang.annotation.*;

/**
 * <p>Use this annotation on your controller methods/actions where you wish to filter Cross-site
 * scripting. It will remove all xss from request parameter.</p>
 *
 * @author Bhushan Uniyal.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface XssFilter {

}
