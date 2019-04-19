package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Anton Mikhaylov on 2019-04-19.
 */
public class SingleThreadAnalyzer extends QueryAnalyzer {

    public Map<String, Integer> getTargetWordsCount() {


        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {


            reader.readLine(); //skip first line
            String line;

            while ((line = reader.readLine()) != null) {
                processLine(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return targetWordsCount;
    }



}
