# XssRequestFilter [![MIT license](https://img.shields.io/badge/license-GPL_3.0-yellow.svg)](https://github.com/techguy-bhushan/XssRequestFilters/blob/master/LICENSE)

Filter the Cross-site scripting

__Cross-site scripting (XSS)__ is a type of computer security vulnerability typically found in web applications. XSS enables attackers to inject client-side scripts into web pages viewed by other users. A cross-site scripting vulnerability may be used by attackers to bypass access controls such as the same-origin policy[..read more...](https://en.wikipedia.org/wiki/Cross-site_scripting)


__XssRequestFilter__ is a spring based lib to filter the cross-site scripting in your @Controller /@RestController request (current version have xss filter support for form data and json request) just using a simple @XssFilter annotation . Also, it's easy to customized with own custom rules for filter xss request.


Use @XssFilter annotation on your controller methods where you wish to filter  Cross-site scripting.
It will remove all xss from request parameter. Also use  `@ComponentScan("com.xss.filters")` in your one of configuration class for active the autoconfiguration for XssRequestFilter.

example:
```
@Configuration
@ComponentScan("com.xss.filters")
public class Config {
}

````


```

@RestController
public class Api {

  /**
   * Test with Content-Type: application/x-www-form-urlencoded
   * Sample Test Curl Request:
   * curl --location --request POST 'http://localhost:8080/test-with-model-attribute' \
   * --header 'Content-Type: application/x-www-form-urlencoded' \
   * --data-urlencode 'name=data <div id=\"demo\">Inject Html</div>'
   *
   *  Response :
   *  {
   *     "name": "data "
   * }
   *
   * you can see in response sample <div id=\"demo\">Inject Html</div> removed by XssFilter
   * */
  @XssFilter
  @RequestMapping(value = "/test-with-model-attribute", method = RequestMethod.POST)
  public Person save(@ModelAttribute Person person) {
    return new Person(person.getName());
  }

  /**
   * Test with Content-Type: application/json
   *
   * Curl request :
   * curl --location --request POST 'http://localhost:8080/test-with-request-body' \
   * --header 'Content-Type: application/json' \
   * --data-raw '{
   *     "name" : "Bhushan <div id=\"demo\">Inject Html</div>"
   * }'
   *
   * Response of API :
   * {
   *     "name": "Bhushan "
   * }
   *
   * Here in response result :  <div id=\"demo\">Inject Html</div> removed by XssFilter
   * */
  @XssFilter
  @PostMapping("/test-with-request-body")
  public Person personNameProcess(@RequestBody Person request,
      HttpServletRequest httpServletRequest) {
    return new Person(request.getName());
  }


  public static class Person {

    private String name;

    public Person() {
    }

    public Person(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }

}


```


Note : `@XssFilter` bound with Url Patterns so it can process matched url with different type of request method.
 
 
## Following regex pattern (most of them are case-insensitive Pattern) are used for filter xss value :

* `(<input(.*?)></input>|<input(.*)/>)`

* `<span(.*?)</span>`

* `<div(.*)</div>`

* `<style>(.*?)</style>`

* `<script>(.*?)</script>`

* `javascript:`

* `</script>`

* `<script(.*?)>`

* `src[\r\n]*=[\r\n]*\\\'(.*?)\\\'`

* `eval\((.*?)\)`

* `expression\((.*?)\)`

* `vbscript:`

* `onload(.*?)=`

[XSS Filter Evasion Cheat Sheet](https://www.owasp.org/index.php/XSS_Filter_Evasion_Cheat_Sheet)

If above patterns are not enough for you, and you want your own custom pattern for filter xss request, then no worry XssRequestFilter also support 
your custom logic for filter your xss request.

## Create your own custom logic for filter xss request:

By default this framework you [DefaultRansackXssImpl](https://github.com/techguy-bhushan/XssRequestFilters/blob/master/src/main/java/com/xss/filters/service/DefaultRansackXssImpl.java)
service for filter the xss request, this service implemented **RansackXss** interface.

So for your custom logic for filter xss request you just need following steps:
1. Create a class which will implement the RansackXss interface.
2. Implement the `String ransackXss(String value);` method. (value parameter is ServletRequest parameter where client can inject the xss, you need to perform the filter on this value, you can take a reference of DefaultRansackXssImpl class)
3. Create a bean of this class 

done... Now instead of DefaultRansackXssImpl, RansackXss will use your class implementation rules for filter xss

### Supported Request content type : (version 1.1.0)
* application/x-www-form-urlencoded
* application/json

## Download it from here  

* Apache Maven  
  ```xml
    <dependency>
     <groupId>com.github.techguy-bhushan</groupId>
     <artifactId>xss.filter</artifactId>
     <version>1.1.0</version>
    </dependency> 
  ```
 
 * Gradle/Grails
 
 `compile 'com.github.techguy-bhushan:xss.filter:1.1.0'`

  
## Here are some useful classes used in XssRequestFilter

### XssFiltersConfiguration :
 This Component will search all the url's which action are annotated with `@XssFilter` (collect the list of urls, which will be pick by CustomXssFilter )
 
### CustomXssFilter:
 This filter will only work for request which action have annotated `@XssFilter` (with help of XssFiltersConfiguration)
 
### CaptureRequestWrapper :
 This class is responsible for filter the XSS in request you can add or remove the XSS handling logic in #stripXSS method  in CaptureRequestWrapper,  `CustomXssFilter` use this class for remove xss in request.
 
### FilterConfig : 
  This component will register CustomXssFilter if there will any @XssFilter annotation used in url mapping, if there will no @XssFilter used in application then `CustomXssFilter` will disable.

### ServletInputStreamXssFilterProvider
 This component has list of ServletInputStreamXssFilterProvider which are responsible for process ServletInputStream based on supported content types, you can customize this component based on your own requirement.
  
  
   
## Please create a new issue if you found any issue, also you can create a pull request from improvement. 

Thank you!
 
