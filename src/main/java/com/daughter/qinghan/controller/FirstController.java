package com.daughter.qinghan.controller;

import com.daughter.qinghan.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/qinghan")
public class FirstController {

    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/say")
    public String qinghanSay() {
        return "my daughter say Dady";
    }


    @RequestMapping(value = "/task")
    public void task() {
        taskService.taskGroupName();
    }
}
