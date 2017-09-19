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

 The filter will pick up only those request whose have annotated with @XxsFilter annotation.
