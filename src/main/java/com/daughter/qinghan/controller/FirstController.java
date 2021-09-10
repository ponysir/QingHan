package com.daughter.qinghan.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.daughter.qinghan.service.TaskService;
import com.daughter.qinghan.utils.WebUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/qinghan")
public class FirstController {

    @Autowired
    private TaskService taskService;

    @NacosValue(value = "${zkz}")
    private String name;

    @RequestMapping(value = "/say")
    public String qinghanSay() {
        System.out.println(WebUntil.headMap());
        return "my daughter say Dady";
    }


    @RequestMapping(value = "/task")
    public void task() {
        taskService.taskGroupName();
    }


    @RequestMapping(value = "/exprt")
    public void exprt() {
        taskService.taskGroupName();
    }
}
