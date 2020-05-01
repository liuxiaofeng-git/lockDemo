package com.atguigu.lock.demo05;

import org.springframework.util.StringUtils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class myResourse {
    private AtomicInteger atomicInteger = new AtomicInteger();
    private volatile Boolean flag = true;//默认开启生产者消费者模式
    BlockingQueue<String> blockingQueue = null;

    public myResourse(BlockingQueue<String> blockingQueue) throws Exception {
        this.blockingQueue = blockingQueue;
        System.out.println(Thread.currentThread().getName());
    }

    public void prod() throws InterruptedException {
        String data = null;
        Boolean retValue;
        while (flag) {
            data = atomicInteger.incrementAndGet() + "";
            retValue = blockingQueue.offer(data, 2, TimeUnit.SECONDS);
            if (retValue) {
                System.out.println(Thread.currentThread().getName() + "\t生产者生产" + data + "成功");
            } else {
                System.out.println(Thread.currentThread().getName() + "\t生产者生产" + data + "失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName() + "大老板生产叫停，表示flag=false");
    }

    public void consumer() throws Exception {
        String result = null;
        while (flag) {
            result = blockingQueue.poll(2, TimeUnit.SECONDS);
            if (StringUtils.isEmpty(result)) {
                flag = false;
                System.out.println(Thread.currentThread().getName() + "\t没有获取到蛋糕");
                return;
            }
            System.out.println(Thread.currentThread().getName() + "\t消费蛋糕" + result + "成功");
        }
    }
    public void stop(){
        flag=false;
    }

}

public class ThreadCommunicationDemo {
    public static void main(String[] args) throws Exception {
        myResourse myResourse = new myResourse(new ArrayBlockingQueue<String>(10));
        new Thread(() -> {
            try {
                myResourse.prod();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "prod").start();

        new Thread(() -> {
            try {
                myResourse.consumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "consumer").start();

        TimeUnit.SECONDS.sleep(10);
        myResourse.stop();
    }
}
