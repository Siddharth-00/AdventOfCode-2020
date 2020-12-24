import java.math.BigDecimal
import kotlin.math.sqrt

fun findDestination(commands: String): Pair<BigDecimal, BigDecimal> {
    var i = 0
    var x = 0.0.toBigDecimal()
    var y = 0.0.toBigDecimal()
    val h = (sqrt(3.0) / 2.0).toBigDecimal()
    while (i < commands.length) {
        when (commands[i]) {
            'e' -> x += 1.0.toBigDecimal()
            'w' -> x -= 1.0.toBigDecimal()
            'n' ->
                if (commands[i + 1] == 'e') {
                    x += 0.5.toBigDecimal()
                    y += h
                    i += 1
                } else {
                    x -= 0.5.toBigDecimal()
                    y += h
                    i += 1
                }
            's' ->
                if (commands[i + 1] == 'e') {
                    x += 0.5.toBigDecimal()
                    y -= h
                    i += 1
                } else {
                    x -= 0.5.toBigDecimal()
                    y -= h
                    i += 1
                }
        }
        i += 1
    }
    return x to y
}

fun findBlack(input: List<String>): Int {
    val coordinates: MutableMap<Pair<BigDecimal, BigDecimal>, Boolean> = mutableMapOf()
    input.forEach {
        val destination = findDestination(it)
        if (destination in coordinates) {
            coordinates[destination] = ! coordinates[destination] !!
        } else coordinates[destination] = true
    }
    return coordinates.values.filter { it }.size
}

fun simulate(input: List<String>, times: Int): Int {
    val coordinates: MutableMap<Pair<BigDecimal, BigDecimal>, Boolean> = mutableMapOf()
    input.forEach {
        val destination = findDestination(it)
        if (destination in coordinates) coordinates[destination] = ! coordinates[destination] !!
        else coordinates[destination] = true
    }
    val addList = mutableListOf<Pair<BigDecimal, BigDecimal>>()
    coordinates.keys.forEach { c ->
        val adjacent = getAdjacent(c)
        adjacent.forEach { a -> if (a !in coordinates) addList.add(a) }
    }
    addList.forEach { a -> coordinates[a] = false }
    repeat(times) {
        val flipList = coordinates.keys.filter { coordinate ->
            if (coordinates[coordinate] !!) getAdjacent(coordinate).count {
                coordinates.getOrDefault(it, false)
            } !in 1..2
            else getAdjacent(coordinate).count { coordinates.getOrDefault(it, false) } == 2
        }
        flipList.forEach { c -> coordinates[c] = ! coordinates[c] !! }
        val addList = mutableListOf<Pair<BigDecimal, BigDecimal>>()
        coordinates.keys.forEach { c ->
            val adjacent = getAdjacent(c)
            adjacent.forEach { a -> if (a !in coordinates) addList.add(a) }
        }
        addList.forEach { a -> coordinates[a] = false }
    }
    return coordinates.values.count { it }
}

fun getAdjacent(start: Pair<BigDecimal, BigDecimal>): List<Pair<BigDecimal, BigDecimal>> {
    val h = (sqrt(3.0) / 2.0).toBigDecimal()
    val x = start.first
    val y = start.second
    val one = 1.toBigDecimal()
    val half = 0.5.toBigDecimal()
    return listOf(
        (x + one)  to y,
        (x - one)  to y,
        (x + half) to (y + h),
        (x + half) to (y - h),
        (x - half) to (y + h),
        (x - half) to (y - h)
    )
}

fun main() {
    val input = ReadInput("input24.txt").readListOfStr()
    println(simulate(input, 100))
}
