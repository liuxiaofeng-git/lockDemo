package com.atguigu.lock.demo02;

import lombok.Getter;

import java.util.concurrent.CountDownLatch;

enum CountryEnum {
    ONE(1, "齐"), TWO(2, "楚"), THREE(3, "燕"), FOUR(4, "韩"), FIVE(5, "赵"), SIX(6, "魏");

    @Getter
    private Integer retCode;
    @Getter
    private String retMesaage;

    CountryEnum(Integer retCode, String retMesaage) {
        this.retCode = retCode;
        this.retMesaage = retMesaage;
    }

    public static CountryEnum forEach_Enum(Integer index) {
        CountryEnum[] values = CountryEnum.values();
        for (CountryEnum enumElement : values) {
            if (index == enumElement.retCode) {
                return enumElement;
            }
        }
        return null;
    }
}

public class CountDownLatchDemo {
    private static final int COUNT = 6;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(COUNT);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t同学出来了");
                countDownLatch.countDown();
            }, CountryEnum.forEach_Enum(i).getRetMesaage()).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t秦，关门");

    }
}
