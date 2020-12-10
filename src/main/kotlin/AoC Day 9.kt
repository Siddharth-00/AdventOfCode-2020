import java.math.BigInteger

fun part1(input: Array<BigInteger>): Int {
    val preamble: MutableList<BigInteger> = mutableListOf()
    for ((i, n) in input.withIndex()) {
        if (i >= 25) {
            val half = n.divide(2.toBigInteger())
            val left: Set<BigInteger> = preamble.filter { it <= half }.map { n - it }.toSet()
            val right: Set<BigInteger> = preamble.filter { it > half }.toSet()
            val pairs = left.intersect(right).size
            if (pairs == 0) return n.toInt()
            preamble.removeFirstOrNull()
        }
        preamble.add(n)
    }
    return 0
}

fun part2(input: Array<BigInteger>): BigInteger {
    val target = part1(input).toBigInteger()
    val nums: MutableMap<BigInteger, Int> = mutableMapOf(Pair(0.toBigInteger(), - 1))
    var total = 0.toBigInteger()
    for ((i, n) in input.withIndex()) {
        total += n
        if ((total - target) in nums) {
            val slice = input.slice((nums[total - target] !! + 1)..i)
            return (slice.maxOrNull() !! + slice.minOrNull() !!)
        }
        nums[total] = i
    }
    return 0.toBigInteger()
}

fun main() {
    val input = ReadInput("input9.txt").readListOfBigInts()
    println(part2(input))
}
