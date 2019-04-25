package com.example;


import java.util.Map;

public class Main {

    static final double THRESHOLD = 0.05;
    static final int BUFFER_SIZE = 100000;
    static final int THREADS_COUNT = 100;

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        QueryAnalyzer analyzer = getSingleThreadAnalyzer();
//        QueryAnalyzer analyzer = getCachedMultithreadAnalyzer();
//        QueryAnalyzer analyzer = getFixedMultithreadAnalyzer();

        Map<String, Integer> result = analyzer.sorted(
                analyzer.filter(analyzer.getTargetWordsCount(), THRESHOLD)
        );

        result.forEach((key, value) -> System.out.println(key + " : " + value + " times"));


        System.out.println("\nEnded after " + (System.currentTimeMillis() - start) / 1000  + "s");

    }

    private static QueryAnalyzer getSingleThreadAnalyzer() {
        return new SingleThreadAnalyzer();
    }

    private static QueryAnalyzer getCachedMultithreadAnalyzer() {
        return new MultiThreadAnalyzer(new CachedThreadPoolProvider());
    }

    private static QueryAnalyzer getFixedMultithreadAnalyzer() {
        return new MultiThreadAnalyzer(new FixedThreadPoolProvider(THREADS_COUNT), BUFFER_SIZE);
    }







}