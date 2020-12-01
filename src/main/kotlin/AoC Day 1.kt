import java.io.File

fun findPair(input: Array<Int>, target: Int): Int {
    val storedNums: BooleanArray = BooleanArray(target) { false }
    for (n in input) {
        if(target >= n && storedNums[target - n]) {
            return (target - n) * n
        }
        storedNums[n] = true
    }
    return 0
}

fun findTriplet(input: Array<Int>, target: Int): Int {
    for(i in 2 until input.size) {
        if(input[i] > target) {
            return 0
        }
        if(input[i] != input[i - 1]) {
            val pairSum = findPair(input.sliceArray(0 until i), target - input[i])
            if(pairSum != 0) return pairSum * input[i]
        }
    }
    return 0
}

fun processFile(): Array<Int> {
    val file = File("/Users/siddharthsharma/Documents/GitHub/AdventOfCode-2020/src/main/resources/input1.txt")
    return file.readLines().map { it.toInt() }.toTypedArray()
}

fun main() {
    val startTime = System.nanoTime()
    val input: Array<Int> = processFile()
    println(findTriplet(input, 2020))
    val endTime = System.nanoTime() - startTime
    println("Time: ${endTime * 0.000001}ms")
}