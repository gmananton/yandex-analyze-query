package com.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toMap;

/**
 * Created by Anton Mikhaylov on 2019-04-18.
 */
public abstract class QueryAnalyzer {

    protected final String FILE_PATH = "/Users/gman/Downloads/football";
    protected final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    protected final LocalDateTime start = LocalDateTime.parse("2018-06-14 00:00:00", formatter);
    protected final LocalDateTime end = LocalDateTime.parse("2018-07-15 23:59:59", formatter);
    protected final String WORD_SPLITTER = "\\s";
    protected final String QUERY_AND_DATE_SPLITTER = "\t";

    protected final List<String> baseKeyWords = Arrays.asList(
            "чемпионат",
            "футбол",
            "матч",
            "трансляция",
            "стадион",
            "билет",
            "игрок",
            "турнир таблиц",
            "fifa",
            "world cup",
            "сборн");

    protected final Map<String, Integer> targetWordsCount = new ConcurrentHashMap<>();

    public abstract Map<String, Integer> getTargetWordsCount();


    public Map<String, Integer> sorted(Map<String, Integer> unsorted) {

        Map<String, Integer> result = new LinkedHashMap<>();

        unsorted.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));

        return result;
    }

    /**
     * Убирает из выборки записи, встречающиеся меньше чем указанное пороговое значение
     * (в процентах от общего количества слов)
     *
     * @param words
     * @param threshold
     * @return
     */
    public Map<String, Integer> filter(Map<String, Integer> words, double threshold) {

        double totalCount = (double) words.values().stream().mapToInt(Integer::intValue).sum();

        return words.entrySet()
                .stream()
                .filter(e -> (double) e.getValue() / totalCount >= threshold)
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    protected void processLines(Collection<String> pack) {
        for (String line : pack) {
            processLine(line);
        }
    }

    protected void processLine(String line) {

        String[] parts = line.split(QUERY_AND_DATE_SPLITTER);
        String query = parts[0];
        String date = parts[1];

        if (!dateIsOk(date)) {
            return;
        }

        Arrays.stream(query.split(WORD_SPLITTER)).forEach(word -> {
            if (baseKeyWords.stream().anyMatch(word::contains)) {
                addAndCount(word);
            }
        });
    }

    private boolean dateIsOk(String dateTimeString) {
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
        return !dateTime.isBefore(start) && !dateTime.isAfter(end);
    }

    private void addAndCount(String word) {

        if (targetWordsCount.containsKey(word)) {
            Integer newCount = targetWordsCount.get(word) + 1;
            targetWordsCount.put(word, newCount);
        } else {
            targetWordsCount.put(word, 1);
        }
    }


}
