fun findCID(s: String): Int {
    val row = Integer.parseInt(String(s.dropLast(3).map { if (it == 'F') '0' else '1' }.toCharArray()), 2)
    val col = Integer.parseInt(String(s.takeLast(3).map { if (it == 'L') '0' else '1' }.toCharArray()), 2)
    return row * 8 + col
}

fun findMaxID(passes: List<String>): Int = passes.map { findCID(it) }.maxOrNull()!!

fun findSeat(passes: List<String>): Int {
    val cids: List<Int> = passes.map { findCID(it) }
    for (i in 0..(127*8 + 7)) {
        if(i-1 in cids && i !in cids && i+1 in cids) {
            return i
        }
    }
    return 0
}

fun main() {
    val input = ReadInput("input5.txt").readListOfStr()
    println(findSeat(input))
}