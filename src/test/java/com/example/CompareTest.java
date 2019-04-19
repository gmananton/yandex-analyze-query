package com.example;

import org.junit.Test;

/**
 * Created by Anton Mikhaylov on 2019-04-19.
 */
public class CompareTest {


    @Test
    public void compare() {
        int totalCount = 100000;
        double threshold = 0.5;
        int amount = 40000;

        System.out.println((double) amount /(double) totalCount > threshold);

    }
}
