fun countValid(lines: List<String>, countFunc: (Int, Int, Char, String) -> Boolean): Int = lines.count {
    val num1 = it.substringBefore("-").toInt()
    val num2 = it.substringBefore(" ").substringAfter("-").toInt()
    val ch = it.substringAfter(" ").substringBefore(":")[0]
    val str = it.substringAfter(": ")
    countFunc(num1, num2, ch, str)
}

fun readPasswords1(min: Int, max: Int, ch: Char, str: String): Boolean = str.count { it == ch } in min..max

fun readPasswords2(pos1: Int, pos2: Int, ch: Char, str: String): Boolean = (str.getOrNull(pos1 - 1) == ch).xor(str.getOrNull(pos2 - 1) == ch)

fun main() {
    val timer = ProgramTimer()
    val input = ReadInput("input2.txt").readListOfStr()
    println(countValid(input, ::readPasswords2))
    timer.endTimer()
}
