package com.concurrent;

import com.concurrent.util.PrimeNumberUtil;

import java.util.Scanner;
import java.util.concurrent.*;

public class B_PrimeNumberWithScheduledThreadPool {
    public static void main(String[] args) {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);

        /*
         * newScheduledThreadPool : 주기적으로 실행할 쓰레드 생성
         *                          이전에 Thread.sleep() 으로 실행하던 로직과 동일. root thread를 wait 상태로 점유할 필요 없이 해당 동작 구현 가능.
         */
        ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(1);
        Runnable reportRunnable = () -> {
            System.out.println("running report");

            // ExecutorService를 ThreadPoolExecutor로 cast하여 쓰레드풀 관련 정보를 얻을 수 있다.
            System.out.println("Active threads: " + executorService.getActiveCount());
            System.out.println("Completed threads: " + executorService.getCompletedTaskCount());
        };
        
        // Thread.sleep(5000) 과 동일한 기능
        scheduledExecutor.scheduleAtFixedRate(reportRunnable, 1, 5, TimeUnit.SECONDS);

        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("\n I can tell you the nth prime number. Enter n: ");
            int n = sc.nextInt();
            if (n == 0) break;

            Runnable r = new Runnable() {
                @Override
                public void run() {
                    int number = PrimeNumberUtil.calculatePrime(n);
                    System.out.println("\n Result: ");
                    System.out.println("\n Value of " + n + "th prime: " + number);
                }
            };

            executorService.execute(r);
        }
    }
}