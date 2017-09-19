package com.xssrequestfilters.controller;

import com.xssrequestfilters.customannotation.XxsFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class TestController {

    @XxsFilter
    @RequestMapping("/")
    public ModelAndView save(Model model, BindingResult result, Map map) {
        // logic
        return new ModelAndView();
    }

}
