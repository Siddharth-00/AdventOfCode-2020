import java.math.BigInteger

fun part1(input: IntArray): Int {
    var oneDiff = 0
    var threeDiff = 0
    val sortedInput = input.plus(0).sorted()
    sortedInput.zip(sortedInput.drop(1)).forEach {
        if (it.second - it.first == 1) oneDiff += 1
        if (it.second - it.first == 3) threeDiff += 1
    }
    threeDiff += 1
    return oneDiff * threeDiff
}

fun part2(input: List<Int>, memo: Array<BigInteger>): BigInteger {
    for (n in input) memo[n] += memo[n + 1] + memo[n + 2] + memo[n + 3]
    return memo[0]
}

fun main() {
    val input = ReadInput("input10.txt").readListOfInts()
    val sortedInput = input.plus(0).sortedDescending()
    val memo: Array<BigInteger> = Array(sortedInput.maxOrNull()!! + 4) { 0.toBigInteger() }
    memo[sortedInput.maxOrNull()!!] = 1.toBigInteger()
    println(part2(sortedInput, memo))
}
