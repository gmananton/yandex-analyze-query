package com.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Anton Mikhaylov on 2019-04-19.
 */
public class CachedThreadPoolProvider implements ExecutorServiceProvider {

    @Override
    public ExecutorService getExecutorService() {
        return Executors.newCachedThreadPool();
    }
}
