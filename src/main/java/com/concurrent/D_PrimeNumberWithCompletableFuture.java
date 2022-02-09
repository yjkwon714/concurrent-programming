package com.concurrent;

import com.concurrent.util.PrimeNumberUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class D_PrimeNumberWithCompletableFuture {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("\n I can tell you the nth prime number. Enter n: ");
            int n = sc.nextInt();
            if (n == 0) break;

            /*
             * CompletableFuture를 사용하면 Future를 활용해야 하는 코드가 매우 간단해진다.
             * supplyAsync() 에서 소요되는 시간이 얼마가 걸리던 해당 쓰레드에서 리턴 값 받고 나면 thenAccept() 로직에서 처리
             */
            CompletableFuture.supplyAsync(() -> PrimeNumberUtil.calculatePrime(n), executorService)
                    .thenAccept(System.out::println);
        }
    }
}
