package com.xlproject.modules.web01.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class helloController {
    @GetMapping("/hello")
    public  String sayHi(){
        return "hello，你好";
    }
}
