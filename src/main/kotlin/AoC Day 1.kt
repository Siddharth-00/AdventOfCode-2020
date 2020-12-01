import java.io.File
import ProgramTimer

fun findPair(input: Array<Int>, target: Int): Int {
    val storedNums: BooleanArray = BooleanArray(target) { false }
    for (n in input) {
        if (target >= n && storedNums[target - n]) {
            return (target - n) * n
        }
        storedNums[n] = true
    }
    return 0
}

fun findTriplet(input: Array<Int>, target: Int): Int {
    for (i in 2 until input.size) {
        if (input[i] > target) {
            return 0
        }
        if (input[i] != input[i - 1]) {
            val pairSum = findPair(input.sliceArray(0 until i), target - input[i])
            if (pairSum != 0) return pairSum * input[i]
        }
    }
    return 0
}

fun processFile(): Array<Int> {
    val file = File("/Users/siddharthsharma/Documents/GitHub/AdventOfCode-2020/src/main/resources/input1.txt")
    return file.readLines().map { it.toInt() }.toTypedArray()
}

fun main() {
    val timer = ProgramTimer()
    val input: Array<Int> = processFile()
    println(findTriplet(input, 2020))
    timer.endTimer()
}
