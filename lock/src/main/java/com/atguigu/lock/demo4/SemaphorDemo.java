package com.atguigu.lock.demo4;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphorDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);//模拟三个停车位
            for (int i = 0; i < 6; i++) {
                new Thread(() -> {
                    try {
                        semaphore.acquire();
                        System.out.println(Thread.currentThread().getName()+"\t获得车位");
                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName()+"\t离开车位");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        semaphore.release();
                    }
                }, String.valueOf(i)).start();
            }

    }
}
