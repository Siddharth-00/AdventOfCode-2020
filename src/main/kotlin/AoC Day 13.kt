import java.math.BigDecimal
import java.math.BigInteger

fun findNextBus(input: List<String>): Int {
    val n = input[0].toInt()
    val buses = input[1].split(",")
    val fastest = buses.minByOrNull {
        if (it == "x") 1000000
        else {
            val bus = it.toInt()
            n / bus * bus + bus - n
        }
    }!!.toInt()
    return (n / fastest * fastest + fastest - n) * fastest
}

fun multInv(a: BigDecimal, b: BigInteger): BigDecimal {
    if (b == 1.toBigInteger()) return 1.toBigDecimal()
    var aa = a
    var bb = b
    var x0 = 0.toBigDecimal()
    var x1 = 1.toBigDecimal()
    while (aa > 1.toBigDecimal()) {
        val q = aa / bb.toBigDecimal()
        var t = bb.toBigDecimal()
        bb = aa.toBigInteger() % bb
        aa = t
        t = x0
        x0 = x1 - q * x0
        x1 = t
    }
    if (x1 < 0.toBigDecimal()) x1 += b.toBigDecimal()
    return x1
}

fun chineseRemainder(n: IntArray, a: IntArray): BigInteger {
    val prod = n.fold(1) { acc, i -> acc * i }.toBigInteger()
    var sum: BigInteger = 0.toBigInteger()
    for (i in 0 until n.size) {
        val p = (prod / n[i].toBigInteger()).toBigDecimal()
        sum += (a[i].toBigDecimal() * multInv(p, n[i].toBigInteger()) * p).toBigInteger()
    }
    return sum % prod
}

fun findMinTime(input: List<String>): BigInteger {
    val n = input[0].toInt()
    val buses = input[1].split(",")
    val times: MutableMap<Int, Int> = mutableMapOf()
    var count = 0
    buses.forEach {
        if (it != "x") times[it.toInt()] = (it.toInt() - count) % it.toInt()
        count += 1
    }
    return chineseRemainder(times.keys.toIntArray(), times.values.toIntArray())
}

fun main() {
    val input = ReadInput("input13.txt").readListOfStr()
    println(findMinTime(input))
}
