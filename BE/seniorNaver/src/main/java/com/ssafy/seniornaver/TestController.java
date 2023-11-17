package com.ssafy.seniornaver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("v1/test")
    public String test() {
        log.info("Hello World");
        System.out.println("Hello World");
        return "Hello this is test";
    }

    @GetMapping("/test2")
    public String test2() {
        log.info("Hello World");
        System.out.println("Hello World");
        return "Hello this is test";
    }


    @GetMapping("v1/test3")
    public String test3() {
        log.info("Hello World");
        System.out.println("Hello World");
        return "Hello this is test";
    }
}