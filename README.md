# XssRequestFilter [![MIT license](https://img.shields.io/badge/license-GPL_3.0-yellow.svg)](https://github.com/techguy-bhushan/XssRequestFilters/blob/master/LICENSE)

Filter the Cross-site scripting



__XssRequestFilter__ is a spring based framework to filter the cross-site scripting in your Controller/RestController request just using a simple @XxsFilter annotation . Also it's easy to customized you own rule for filter xss request.


Use @XxsFilter annotation on your controller methods where you wish to filter  Cross-site scripting.
It will remove all xss from request parameter.

example:
```
@Controller
public class TestController {

    @XxsFilter
    @RequestMapping("/")
    public ModelAndView save(Model model, BindingResult result, Map map) {
        // logic
        return new ModelAndView();
    }
    @XxsFilter
        @RequestMapping("/save"
        public ModelAndView save(Model model, BindingResult result, Map map) {
            // logic
            return new ModelAndView();
    }
    
     @XxsFilter
     @RequestMapping("/update")
     public ModelAndView update(Model model, BindingResult result, Map map) {
            // logic
            return new ModelAndView();
     
}

```


The filter will pick up only those request whose have annotated with `@XxsFilter` annotation.
 
 
## Following regex pattern (most of them are case insensitive Pattern) are used for filter xss value :

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

If above patterns are not enough for you and you want your own custom pattern for filter xss request, then no worry XssRequestFilter also support 
your custom logic for filter your xss request.

## Create your own custom logic for filter xss request:

By default this framework you [DefaultRansackXssImpl](https://github.com/techguy-bhushan/XssRequestFilters/blob/master/src/main/java/com/xss/filters/service/DefaultRansackXssImpl.java)
service for filter the xss request, this service implemented **RansackXss** interface.

So for your custom logic for filter xss request you just need following steps:
1. Create a class which will implement the RansackXss interface.
2. Implement the `String ransackXss(String value);` method. (value parameter is ServletRequest parameter where client can inject the xss, you need to perform the filter on this value, you can take a reference of DefaultRansackXssImpl class)
3. Create a bean of this class 

done... Now instead of DefaultRansackXssImpl, RansackXss will use your class implementation rules for filter xss
  
 
## Download it from here  

* Apache Maven  
  ```xml
    <dependency>
     <groupId>com.github.techguy-bhushan</groupId>
     <artifactId>xss.filter</artifactId>
     <version>1.0.1</version>
    </dependency> 
  ```
 
 * Gradle/Grails
 
 `compile 'com.github.techguy-bhushan:xss.filter:1.0.1'`
  
 * [jar](https://search.maven.org/remotecontent?filepath=com/github/techguy-bhushan/xss.filter/1.0.1/xss.filter-1.0.1.jar)
 
  
## Here are some useful classes used in XssRequestFilter

### XssFiltersConfiguration :
 This Component will search all the url's which action are annotated with `@XxsFilter` (collect the list of urls, which will be pick by CustomXssFilter )
 
### CustomXssFilter:
 This filter will only work for request which action have annotated `@XxsFilter` (with help of XssFiltersConfiguration)
 
### CaptureRequestWrapper :
 This class is responsible for filter the XSS in request you can add or remove the XSS handling logic in #stripXSS method  in CaptureRequestWrapper,  `CustomXssFilter` use this class for remove xss in request.
 
### FilterConfig : 
  This component will register CustomXssFilter if there will any @XxsFilte annotation used in url mapping, if there will no @XxsFilte used in application then `CustomXssFilter` will disable.
  
  
   
## Please create a new issue if you found any issue, also you can create a pull request from improvement. 

Thank you!
 
