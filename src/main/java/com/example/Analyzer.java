package com.example;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class Analyzer {

    static double THRESHOLD = 0.02;

    String FILE_PATH = "/Users/gman/Downloads/football";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    String WORD_SPLITTER = "\\s";
    String QUERY_AND_DATE_SPLITTER = "\t";

    // Даты проведения матча
    LocalDateTime start = LocalDateTime.parse("2018-06-14 00:00:00", formatter);
    LocalDateTime end = LocalDateTime.parse("2018-07-15 23:59:59", formatter);

    List<String> baseKeyWords = Arrays.asList(
            "чемпионат", "футбол", "матч", "трансляция",
            "стадион", "билет", "игрок",
            "турнир таблиц", "fifa", "world cup", "сборн");

    //Ключевые слова, сгруппированные по количеству их вхождения во все запросы
    Map<String, Integer> targetWordsCount = new HashMap<>();

    public static void main(String[] args) {

        Analyzer analyzer = new Analyzer();
        long start = System.currentTimeMillis();
        Map<String, Integer> result = analyzer.filter(analyzer.getTargetWordsCount(), THRESHOLD);
        result.forEach((key, value) -> System.out.println(key + " : " + value + " times"));
        System.out.println("\nEnded after " + (System.currentTimeMillis() - start) / 1000  + "s");

    }


    /**
     * Читает файл, анализирует строки, группирует слова по количеству вхождений в запросы
     */
    Map<String, Integer> getTargetWordsCount() {

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


    /**
     * Убирает из выборки записи, встречающиеся меньше чем указанное пороговое значение
     * (в процентах от общего количества слов)
     */
    Map<String, Integer> filter(Map<String, Integer> words, double threshold) {

        double totalCount = (double) words.values().stream().mapToInt(Integer::intValue).sum();

        return words.entrySet()
                .stream()
                .filter(e -> (double) e.getValue() / totalCount >= threshold)
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    /**
     * Обработка строки:
     * - Разбивает ее на запрос и дату запроса
     * - Если дата выбивается за границы проведения чемпионата - пропускаем такой запрос
     * - Если дата подходит, разбиваем запрос на отдельные слова и добавляем в Map с группировкой по количеству данных слов
     */
    void processLine(String line) {

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


    /**
     * Определяет, входит ли указанная дата в границы проведения чемпионата
     */
    boolean dateIsOk(String dateTimeString) {

        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
        return !dateTime.isBefore(start) && !dateTime.isAfter(end);
    }

    /**
     * Добавляет ключевое слово в результирующий Map с группировкой по количеству данных слов
     */
    void addAndCount(String word) {

        if (targetWordsCount.containsKey(word)) {
            Integer newCount = targetWordsCount.get(word) + 1;
            targetWordsCount.put(word, newCount);
        } else {
            targetWordsCount.put(word, 1);
        }
    }


}