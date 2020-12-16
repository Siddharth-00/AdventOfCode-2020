import java.math.BigInteger

fun isValid(n: Int, ranges: MutableMap<Int, MutableList<String>>) = n in ranges

fun generateRange(input: List<String>): MutableMap<Int, MutableList<String>> {
    val ranges: MutableMap<Int, MutableList<String>> = mutableMapOf()
    for (line in input[0].split("\n")) {
        val regex = "([\\w\\s]+): (\\d+)-(\\d+) or (\\d+)-(\\d+)".toRegex()
        val match = regex.find(line) !!.destructured
        val param = match.component1()
        val range1 = match.component2().toInt()..match.component3().toInt()
        val range2 = match.component4().toInt()..match.component5().toInt()
        for (i in range1) {
            if (i in ranges) ranges[i] !!.add(param)
            else {
                ranges[i] = mutableListOf(param)
            }
        }
        for (i in range2) {
            if (i in ranges) ranges[i] !!.add(param)
            else {
                ranges[i] = mutableListOf(param)
            }
        }
    }
    return ranges
}

fun findInvalid(input: List<String>): Int {
    val ranges = generateRange(input)
    return input[2].substringAfter("nearby tickets:\n").split("\n").map { it.split(",") }.flatten().sumBy {
        if (isValid(it.toInt(), ranges)) 0
        else {
            it.toInt()
        }
    }
}

fun cleanInput(input: List<String>): Pair<MutableMap<Int, MutableList<String>>, List<List<Int>>> {
    val ranges = generateRange(input)
    return Pair(
        ranges,
        input[2].substringAfter("nearby tickets:\n").split("\n").map { it.split(",") }.map { it.map { a -> a.toInt() } }
            .filter { it.all { a -> isValid(a, ranges) } }
    )
}

fun findClasses(input: List<String>): BigInteger {
    val myNums = input[1].split("\n")[1].split(",").map { it.toInt() }
    val typesRange = cleanInput(input)
    val ranges = typesRange.first
    val otherTickets = typesRange.second.flatten()
    val length = myNums.size
    val allTypes = input[0].split("\n").map { it.substringBefore(":") }.toMutableSet()
    val classes = MutableList(length) { allTypes }
    for ((i, n) in otherTickets.withIndex()) {
        classes[i % length] = classes[i % length].intersect(ranges[n] !!) as MutableSet<String>
    }
    val ordClass = MutableList(length) { "" }
    while (classes != MutableList<MutableSet<String>>(length) { mutableSetOf() }) {
        var removed = ""
        for ((i, l) in classes.withIndex()) {
            if (l.size == 1) {
                ordClass[i] = l.first()
                removed = l.first()
                break
            }
        }
        classes.forEach { it.remove(removed) }
    }
    var output = 1.toBigInteger()
    for ((i, s) in ordClass.withIndex()) {
        if (s.contains("departure")) output *= myNums[i].toBigInteger()
    }
    return output
}

fun main() {
    val input = ReadInput("input16.txt").readGroupsStr()
    println(findClasses(input))
}
