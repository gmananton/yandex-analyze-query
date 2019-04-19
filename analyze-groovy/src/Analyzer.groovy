import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.ConcurrentHashMap

THRESHOLD = 0.05

FILE_PATH = "/Users/gman/Downloads/football"
formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
start = LocalDateTime.parse("2018-06-14 00:00:00", formatter)
end = LocalDateTime.parse("2018-07-15 23:59:59", formatter)
WORD_SPLITTER = "\\s"
QUERY_AND_DATE_SPLITTER = "\t"

baseKeyWords = Arrays.asList(
        "чемпионат", "футбол", "матч", "трансляция",
        "стадион", "билет", "игрок", "турнир таблиц",
        "fifa", "world cup", "сборн")

targetWordsCount = new HashMap<String, Integer>()

