import java.math.BigInteger
import kotlin.math.pow

fun binaryToDecimal(bin: String): BigInteger {
    var output = 0.toBigInteger()
    for ((i, n) in bin.reversed().withIndex()) {
        output += (n.toString().toInt().toBigInteger() * 2.toBigDecimal().pow(i).toBigInteger())
    }
    return output
}

fun applyMask(mask: String, num: Int): BigInteger {
    val numBin = num.toString(2).padStart(36, '0').reversed()
    var output = 0.toBigInteger()
    for (i in numBin.indices) {
        output += if (mask[i] == 'X') (numBin[i].toString().toInt().toBigInteger() * 2.toBigDecimal().pow(i).toBigInteger())
        else {
            (mask[i].toString().toInt().toBigInteger() * 2.toBigDecimal().pow(i).toBigInteger())
        }
    }
    return output
}

fun applyMask2(mask: String, num: Int): List<BigInteger> {
    var outputs: List<String> = listOf("")
    val numBin = num.toString(2).padStart(36, '0').reversed()
    for (i in numBin.indices) {
        outputs = when {
            mask[i] == 'X' -> {
                outputs.map { it + '0' } + outputs.map { it + '1' }
            }
            mask[i] == '0' -> {
                outputs.map { it + numBin[i] }
            }
            else -> {
                outputs.map { it + mask[i] }
            }
        }
    }
    return outputs.map { binaryToDecimal(it.reversed()) }
}

fun processMask(mask: String): Pair<BigInteger, BigInteger> {
    val mask0 = mask.replace('X', '0')
    val mask1 = mask.replace('X', '1')
    val m0 = binaryToDecimal(mask0)
    val m1 = binaryToDecimal(mask1)
    return Pair(m0, m1)
}

fun processProgram(input: List<String>): BigInteger {
    val map: MutableMap<Int, BigInteger> = mutableMapOf()
    // var m0 = 0.toBigInteger()
    // var m1 = 0.toBigInteger()
    var mask = ""
    input.forEach {
        if (it.substringBefore(" =") == "mask") {
            mask = it.substringAfter("= ").reversed()
        } else {
            val regex = "mem\\[(\\d+)\\] = (\\d+)".toRegex()
            val match = regex.find(it)!!.destructured
            // map[match.component1().toInt()] = ((match.component2().toInt().toBigInteger().or(m0)).and(match.component2().toInt().toBigInteger().or(m1)))
            map[match.component1().toInt()] = applyMask(mask, match.component2().toInt())
        }
    }
    var output = 0.toBigInteger()
    for (v in map.values) output += v
    return output
}

fun processProgram2(input: List<String>): BigInteger {
    val map: MutableMap<BigInteger, Int> = mutableMapOf()
    // var m0 = 0.toBigInteger()
    // var m1 = 0.toBigInteger()
    var mask = ""
    input.forEach {
        if (it.substringBefore(" =") == "mask") {
            mask = it.substringAfter("= ").reversed()
        } else {
            val regex = "mem\\[(\\d+)\\] = (\\d+)".toRegex()
            val match = regex.find(it)!!.destructured
            // map[match.component1().toInt()] = ((match.component2().toInt().toBigInteger().or(m0)).and(match.component2().toInt().toBigInteger().or(m1)))
            for (address in applyMask2(mask, match.component1().toInt())) map[address] = match.component2().toInt()
        }
    }
    var output = 0.toBigInteger()
    println(map.size)
    for (v in map.values) output += v.toBigInteger()
    return output
}

fun main() {
    val input = ReadInput("input14.txt").readListOfStr()
    println(processProgram2(input))
}
