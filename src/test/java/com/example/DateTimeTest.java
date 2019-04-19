package com.example;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

/**
 * Created by Anton Mikhaylov on 2019-04-18.
 */
public class DateTimeTest {

    @Test
    public void test() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse("2018-07-13 11:05:11", formatter);


        assertEquals(2018, dateTime.getYear());
        assertEquals(7, dateTime.getMonthValue());
        assertEquals(13, dateTime.getDayOfMonth());

        assertEquals(11, dateTime.getHour());
        assertEquals(5, dateTime.getMinute());
        assertEquals(11, dateTime.getSecond());
    }
}
