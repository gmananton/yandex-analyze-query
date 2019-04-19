package com.example;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Anton Mikhaylov on 2019-04-18.
 */
public class SplitStringTest {

    final String logString = "ark benefit note1 white	2018-07-03 14:38:28";

    @Test
    public void test() {




        String query = logString.substring(0, logString.length() - 20);
        String date = logString.substring(logString.length() - 19);
//        System.out.println(query);
//        System.out.println(date);

        Set<String> queryWords = new HashSet<>(Arrays.asList(query.replace(" ", "\t").split("\t")));

        for (String queryWord : queryWords) {
            System.out.println(queryWord);
        }
    }



}
