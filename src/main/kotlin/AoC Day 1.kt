import java.io.File

fun findPair(input: Array<Int>): Int {
    val storedNums: BooleanArray = BooleanArray(2020) { false }
    for (n in input) {
        if(storedNums[2020 - n]) {
            return (2020 - n) * n
        }
        storedNums[n] = true
    }
    return 0
}

fun processFile(): Array<Int> {
    val file = File("/Users/siddharthsharma/Documents/GitHub/AdventOfCode/src/main/resources/input1.txt")
    return file.readLines().map { it.toInt() }.toTypedArray()
}

fun main() {
    val input: Array<Int> = processFile()
    println(findPair(input))
}