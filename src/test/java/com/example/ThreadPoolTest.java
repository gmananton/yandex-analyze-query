package com.example;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Anton Mikhaylov on 2019-04-19.
 */
public class ThreadPoolTest {

    @Test
    public void test() throws Exception {


        int tasksNumber = 100;

        CountDownLatch latch = new CountDownLatch(tasksNumber);

//        ExecutorService pool = Executors.newCachedThreadPool();
        ExecutorService pool = Executors.newFixedThreadPool(tasksNumber);


//        List<Future<?>> futures = new LinkedList<>();

        for (int i = 0; i < tasksNumber; i++) {

            final int taskNum = i;

            Future<?> future = pool.submit(() -> {

                int iterations = new Random().nextInt(60) + 59;
                int timeOut = new Random().nextInt(501) + 500;

                longAction(taskNum, iterations, timeOut);
                latch.countDown();
            });

        }


        latch.await();
        pool.shutdown(); // shutdown the pool.

    }





    void longAction(int taskID, int iterations, int timeout) {

        for (int i = 0; i < iterations; i++) {
            System.out.println("Performig task_" + taskID + " / " + Thread.currentThread().getName());
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
