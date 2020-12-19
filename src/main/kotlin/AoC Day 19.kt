fun makeRules(input: List<String>): MutableMap<Int, List<String>> {
    val rules: MutableMap<Int, List<String>> = mutableMapOf()
    for (line in input[0].split("\n")) {
        val regex = "(\\d+): (.+)".toRegex()
        val match = regex.find(line) !!.destructured
        rules[match.component1().toInt()] = match.component2().split("|").map { it.trim() }
    }
    return rules
}

val possibles: MutableMap<Int, List<String>> = mutableMapOf()
fun findPossible(rules: MutableMap<Int, List<String>>, n: Int): List<String> {
    return when {
        n in possibles -> {
            possibles[n] !!
        }
        rules[n] !![0].contains('"') -> {
            possibles[n] = listOf(rules[n] !![0][1].toString())
            possibles[n] !!
        }
        else -> {
            val p = rules[n] !!.map {
                val parts = it.split(" ")
                val p1 = findPossible(rules, parts[0].toInt())
                val p2 = if (parts.size == 1) listOf("") else findPossible(rules, parts[1].toInt())
                p1.map { a -> p2.map { b -> a + b } }.flatten()
            }.flatten()
            possibles[n] = p
            p
        }
    }
}

fun checkValid(input: List<String>): Int {
    findPossible(makeRules(input), 0)
    val a = possibles[42] !!.toSet()
    val b = possibles[31] !!.toSet()
    return input[1].split("\n").count {
        var output = true
        var is31 = false
        if (it.length % 8 != 0) output = false
        var num31 = 0
        var curr = 0
        while (curr < it.length) {
            if (is31) {
                if (it.substring(curr, curr + 8) !in b) output = false
                num31 += 1
            } else {
                if (it.substring(curr, curr + 8) !in a) {
                    if (curr <= 8) output = false
                    else {
                        is31 = true
                        curr -= 8
                    }
                }
            }
            curr += 8
        }
        output && (num31 > 0) && (num31 < (it.length / 8 - num31))
    }
}

fun main() {
    val input = ReadInput("input19.txt").readGroupsStr()
    println(checkValid(input))
}
