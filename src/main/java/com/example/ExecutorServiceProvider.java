package com.example;

import java.util.concurrent.ExecutorService;

/**
 * Created by Anton Mikhaylov on 2019-04-19.
 */
public interface ExecutorServiceProvider {
    ExecutorService getExecutorService();
}
