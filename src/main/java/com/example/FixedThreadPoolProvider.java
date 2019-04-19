package com.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Anton Mikhaylov on 2019-04-19.
 */
public class FixedThreadPoolProvider implements ExecutorServiceProvider {

    private int threadCount = 4; //default value

    public FixedThreadPoolProvider(int threadCount) {
        this.threadCount = threadCount;
    }

    public void setThreadCount(int count) {
        this.threadCount = count;
    }

    @Override
    public ExecutorService getExecutorService() {
        return Executors.newFixedThreadPool(threadCount);
    }
}
