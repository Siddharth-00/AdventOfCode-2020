import java.math.BigInteger
import java.util.ArrayDeque

val opRanks = mapOf('*' to 4, '+' to 5, '(' to 1, ')' to 1)
val ops = "*+"
val opFuncs: Map<Char, (BigInteger, BigInteger) -> BigInteger> =
    mapOf('*' to { a, b -> a * b }, '+' to { a, b -> a + b })

fun eval(exp: String): BigInteger {
    val opStack = ArrayDeque<Char>()
    val expStack = ArrayDeque<Char>()

    for (c in exp.filter { it != ' ' }) {
        when {
            c.isDigit() -> expStack.push(c)
            c in ops -> {
                while (opStack.peek() != null && opRanks[opStack.peek()] !! >= opRanks[c] !! && opStack.peek() != '(') {
                    expStack.push(opStack.pop())
                }
                opStack.push(c)
            }
            c == '(' -> opStack.push(c)
            c == ')' -> {
                while (opStack.peek() != '(') expStack.push(opStack.pop())
                opStack.pop()
            }
        }
    }
    while (opStack.peek() != null) {
        expStack.push(opStack.pop())
    }
    val numStack = ArrayDeque<BigInteger>()
    for (c in expStack.reversed()) {
        if (c.isDigit()) numStack.push((c.toInt() - 48).toBigInteger())
        else {
            numStack.push(opFuncs[c] !!(numStack.pop(), numStack.pop()))
        }
    }
    return numStack.peek()
}

fun processLines(input: List<String>): BigInteger {
    var output = 0.toBigInteger()
    input.forEach { output += eval(it) }
    return output
}

fun main() {
    val input = ReadInput("input18.txt").readListOfStr()
    println(processLines(input))
}
