package com.concurrent;

import com.concurrent.util.PrimeNumberUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class C_PrimeNumberWithFutureObject {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Future<Integer>> futures = new ArrayList<>();

        ExecutorService executorService = Executors.newCachedThreadPool();

        while (true) {
           Scanner sc = new Scanner(System.in);
           System.out.println("\n I can tell you the nth prime number. Enter n: ");
           int n = sc.nextInt();
           if (n == 0) break;

           // Runnable vs Callable : 리턴 값 존재 여부, Exception throw 가능 여부
           Callable<Integer> c = new Callable<Integer>() {
               @Override
               public Integer call() throws Exception {
                   return PrimeNumberUtil.calculatePrime(n);
               }
           };

           // Future 객체에서 바로 값을 가져오도록 하면 값을 리턴받을 때까지 해당 쓰레드에서 대기 시간이 발생하므로 List에 미리 넣어둔다.(쓰레드 풀 모두 활용 불가)
           Future<Integer> primeNumberFuture = executorService.submit(c);
           futures.add(primeNumberFuture);

           // 해당 쓰레드의 작업이 끝나서 값을 리턴받으면 출력한다.
           Iterator<Future<Integer>> iterator = futures.iterator();
           while (iterator.hasNext()) {
               Future<Integer> f = iterator.next();
               if (f.isDone()){
                   System.out.println(f.get());
                   iterator.remove();
               }
           }

       }
    }
}
