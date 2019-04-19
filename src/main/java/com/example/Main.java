package com.example;


import lombok.SneakyThrows;

import java.util.Map;

public class Main {

    static final double THRESHOLD = 0.05;
    static final int BUFFER_SIZE = 1000000;
    static final int THREADS_COUNT = 100;

    public static void main(String[] args) {



        long start = System.currentTimeMillis();

        QueryAnalyzer analyzer = new SingleThreadAnalyzer();

//        ExecutorServiceProvider fixedPool = new FixedThreadPoolProvider(10);
//        ExecutorServiceProvider cachedPool = new CachedThreadPoolProvider();

//        QueryAnalyzer analyzer = new MultiThreadAnalyzer(fixedPool, BUFFER_SIZE);


        Map<String, Integer> result = analyzer.sorted(
                analyzer.filter(analyzer.getTargetWordsCount(), THRESHOLD)
        );

        result.forEach((key, value) -> System.out.println(key + " : " + value + " times"));


        System.out.println("\nEnded after " + (System.currentTimeMillis() - start) / 1000  + "s");

    }


}