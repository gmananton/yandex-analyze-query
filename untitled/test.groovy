
line = "w1 w2 w3;2018-06-20"

baseWords = ["w1", "w2", "w5"]

wordsAndDateArray = line.split(";")

println("Words: " + wordsAndDateArray[0])
println("Date: " + wordsAndDateArray[1])

//process date

//process words
wordsString = wordsAndDateArray[0]
words = wordsString.split("\\s")

for (w in words) {
    println(w)
}

//w1, w2

100
Theshold = 0,05


/*
{
    "w1": 6,
    "w2": 1123123,
    "w5": 512312
    "w6": 2
}
 */

