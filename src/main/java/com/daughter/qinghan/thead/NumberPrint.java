package com.daughter.qinghan.thead;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
 class NumberPrint implements Runnable{
    private int number;
    public byte obj[];
    public static int count = 5;



    @Override
    public void run() {
        synchronized (obj){
            while (count-->0){
                try {
                    obj.notify();//唤醒等待res资源的线程，把锁交给线程（该同步锁执行完毕自动释放锁）
                    System.out.println(" "+number);
                    obj.wait();//释放CPU控制权，释放res的锁，本线程阻塞，等待被唤醒。
                    System.out.println("------线程"+Thread.currentThread().getName()+"获得锁，wait()后的代码继续运行："+number);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String args[]){
        final byte a[] = {0};//以该对象为共享资源
        new Thread(new NumberPrint((1),a),"1").start();
        new Thread(new NumberPrint((2),a),"2").start();

        Map<String,Object>map=new HashMap<>();
        map.forEach((k,v)->
            System.out.println(k)
        );
    }


}

