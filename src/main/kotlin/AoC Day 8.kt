fun acc(accumulator: Int, position: Int, argument: Int): Pair<Int, Int> = Pair(accumulator + argument, position + 1)
fun jmp(accumulator: Int, position: Int, argument: Int): Pair<Int, Int> = Pair(accumulator, position + argument)
fun nop(accumulator: Int, position: Int, argument: Int): Pair<Int, Int> = Pair(accumulator, position + 1)

val commands: Map<String, (Int, Int, Int) -> Pair<Int, Int>> = mapOf("acc" to ::acc, "jmp" to ::jmp, "nop" to ::nop)

fun increment(accumulator: Int, position: Int, line: String): Pair<Int, Int> {
    val command = line.substringBefore(" ")
    val argument = line.substringAfter(" ").toInt()
    return if (command in commands) commands[command]!!(accumulator, position, argument)
    else Pair(accumulator, position)
}

fun doesLoop(input: List<String>): Pair<Int, Boolean> {
    var accumulator = 0
    var position = 0
    val visited: MutableList<Int> = mutableListOf()
    while (position !in visited && position in input.indices) {
        val accPos = increment(accumulator, position, input[position])
        visited.add(position)
        if (position == input.size - 1 && accPos.second >= input.size) return accumulator to false
        accumulator = accPos.first
        position = accPos.second
    }
    return accumulator to true
}

fun swapLine(line: String): String {
    return when (line.substringBefore(" ")) {
        "jmp" -> "nop " + line.substringAfter(" ")
        "nop" -> "jmp " + line.substringAfter(" ")
        else -> line
    }
}

fun part1(input: List<String>): Int = doesLoop(input).first

fun part2(input: List<String>): Int {
    val mutableInput = input.toMutableList()
    for ((i, s) in mutableInput.withIndex()) {
        if (swapLine(s) != s) {
            mutableInput[i] = swapLine(s)
            val check = doesLoop(mutableInput.toList())
            if (!check.second) return check.first
            mutableInput[i] = s
        }
    }
    return 0
}

fun main() {
    val input = ReadInput("input8.txt").readListOfStr()
    println(part2(input))
}
