package com.concurrent;

import com.concurrent.util.PrimeNumberUtil;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class A_PrimeNumberWithThreadPool {
    public static void main(String[] args) {
        /*
         * newFixedThreadPool : 고정된 개수의 쓰레드풀 생성
         * newCachedThreadPool : 쓰레드 개수보다 작업 개수가 많으면 새로 쓰레드 생성, 60초 동안 아무 작업이 없으면 추가된 쓰레드를 종료하고 풀에서 제거
         * newSingleThreadExecutor : 1개 쓰레드풀 생성
         */

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        // ExecutorService executorService = Executors.newCachedThreadPool();
        // ExecutorService executorService = Executors.newSingleThreadExecutor();


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