package com.daughter.qinghan.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "qinghan")
public class FirstController {



    @RequestMapping(value = "say")
    public String  qinghanSay(){
       return "my daughter say Dady";
    }
}
