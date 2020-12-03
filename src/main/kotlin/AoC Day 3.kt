import java.math.BigInteger

fun countTrees(grid: Array<CharArray>, slope: Pair<Int, Int>): Int {
    var i = 0
    var j = 0
    val height = grid.size
    val width = grid[0].size
    var count = 0
    while (i < height) {
        if (grid[i][j] == '#') count += 1
        i += slope.second
        j = (j + slope.first) % width
    }
    return count
}

fun countAllSlopes(grid: Array<CharArray>): BigInteger {
    val slopes = listOf(Pair(1, 1), Pair(3, 1), Pair(5, 1), Pair(7, 1), Pair(1, 2))
    return slopes.map { countTrees(grid, it) }.fold(BigInteger.ONE, { total, next -> total * next.toBigInteger() })
}

fun main() {
    val timer = ProgramTimer()
    val input = ReadInput("input3.txt").readGrid()
    println(countAllSlopes(input))
    timer.endTimer()
}
