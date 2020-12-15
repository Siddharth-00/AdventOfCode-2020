fun day15(n: Int): Int {
    val numbers = mutableMapOf(2 to 1, 1 to 2, 10 to 3, 11 to 4, 0 to 5)
    var mostRecent = 6
    for (i in 6 until n) {
        val nextNum = i - numbers.getOrDefault(mostRecent, i)
        numbers[mostRecent] = i
        mostRecent = nextNum
    }
    return mostRecent
}

fun main() {
    val timer = ProgramTimer()
    println(day15(30000000))
    timer.endTimer()
}
