package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created by Anton Mikhaylov on 2019-04-19.
 */
public class MultiThreadAnalyzer extends QueryAnalyzer {

    private final ExecutorServiceProvider executorServiceProvider;
    private final int BUFFER_SIZE;


    public MultiThreadAnalyzer(ExecutorServiceProvider executorServiceProvider, int BUFFER_SIZE) {
        this.executorServiceProvider = executorServiceProvider;
        this.BUFFER_SIZE = BUFFER_SIZE;
    }

    public Map<String, Integer> getTargetWordsCount() {


        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {

            ExecutorService executor = executorServiceProvider.getExecutorService();
            List<Future<?>> futures = new LinkedList<>();

            reader.readLine(); //skip first line
            String line;

            List<String> buffer = new LinkedList<>();
            while ((line = reader.readLine()) != null) {
                buffer.add(line);
                if (buffer.size() == BUFFER_SIZE) {
                    ArrayList<String> copy = new ArrayList<>(buffer);
                    buffer.clear();

                    Future<?> future = executor.submit(() -> processLines(copy));
                    futures.add(future);
                }
            }

            try {
                for (Future f : futures) {
                    if (f.isDone()) {
                        f.get();
                    }
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } finally {
                executor.shutdown();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return targetWordsCount;
    }
}
