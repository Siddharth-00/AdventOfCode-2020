fun findCID(s: String): Int = Integer.parseInt(String(s.map { if (it == 'F' || it == 'L') '0' else '1' }.toCharArray()), 2)

fun findMaxID(passes: List<String>): Int = passes.map { findCID(it) }.maxOrNull()!!

fun findSeat(cids: List<Int>): Int {
    for (i in 0..(127 * 8 + 7)) if (i - 1 in cids && i !in cids && i + 1 in cids) return i
    return 0
}

fun main() {
    val timer = ProgramTimer()
    val input = ReadInput("input5.txt").readListOfStr()
    val cids = input.map { findCID(it) }
    println(findSeat(cids))
    timer.endTimer()
}
