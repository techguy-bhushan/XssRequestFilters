package com.xss.filter.test_controller;

import com.xss.filter.annotation.XssFilter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

@RestController
@SpringBootApplication
@ComponentScan("com.xss.filter")
public class TestXssApp {

    @PostMapping("/users")
    @XssFilter
    public User user(@RequestBody User user) {
        return user;
    }

    @GetMapping("/users/{userName}")
    public String helloUser(@PathVariable("userName") String userName) {
        return String.format("Hello %s", userName);
    }

}
