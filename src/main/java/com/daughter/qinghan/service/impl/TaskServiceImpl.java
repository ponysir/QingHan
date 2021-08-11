package com.daughter.qinghan.service.impl;

import com.daughter.qinghan.service.TaskService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CountDownLatch;


@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public void taskGroupName() {
        List<Runnable> tasks = Lists.newArrayList();
        final CountDownLatch countDownLatch = new CountDownLatch(6);
        tasks.add(() -> {

        });

        tasks.add(() -> {

        });

        tasks.add(() -> {

        });

        tasks.add(() -> {

        });

        tasks.add(() -> System.out.println(111111));
        try {
            //线程开启
            tasks.forEach(threadPoolTaskExecutor::execute);
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("并行处理失败", e);
        }

    }
}
