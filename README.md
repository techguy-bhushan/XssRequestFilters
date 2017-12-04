# XssRequestFilters
Filter the Cross-site scripting


Use @XxsFilter (custom annotation) annotation on your controller methods where you wish to filter  Cross-site scripting.

ex:

  ```@XxsFilter
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
    }```

 The filter will pick up only those request whose have annotated with `@XxsFilter` annotation.
 
 
 
# XssFiltersConfiguration :
 This Component will search all the url's which action are annotated with `@XxsFilter` (collect the list of urls, which will be pick by CustomXssFilter )
 
# CustomXssFilter:
    filter will only work for request which action have annotated `@XxsFilter` (with help of XssFiltersConfiguration)
 
# CaptureRequestWrapper :
 This class is responsible for filter the XSS in request you can add or remove the XSS handling logic in #stripXSS method  in CaptureRequestWrapper,  `CustomXssFilter` use this class for remove xss in request.
 
# FilterConfig : 
    This component will register CustomXssFilter if there will any @XxsFilte annotation used in url mapping, if there will no @XxsFilte used in application then `CustomXssFilter` will disable.
 
 
