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
        Long startTime=System.currentTimeMillis();
        List<Runnable> tasks = Lists.newArrayList();

        //这里的3是下面开的线程数量
        final CountDownLatch countDownLatch = new CountDownLatch(3);
        tasks.add(() -> {
            for (int i = 0; i <80000 ; i++) {
                System.out.println(i);
            }
            countDownLatch.countDown();
        });
        tasks.add(() -> {
            for (int i = 0; i <80000; i++) {
                System.out.println(i);
            }
            countDownLatch.countDown();

        });

        tasks.add(() ->{
            System.out.println("KKKKKKK");
            countDownLatch.countDown();
        });
        try {
            //线程开启
            tasks.forEach(threadPoolTaskExecutor::execute);
            //主要是等待所有线程结束,再走下面的流程
            countDownLatch.await();
            System.out.println("张克臻"+(System.currentTimeMillis()-startTime));
        } catch (InterruptedException e) {
            log.error("并行处理失败", e);
        }



    }

}
