data class Point(val x: Int, val y: Int, val z: Int, val d: Int = 0)

fun getAdjacent3(p: Point): List<Point> {
    var adjacent: List<Point> = listOf(p)
    adjacent = adjacent.map { Point(it.x + 1, it.y, it.z, it.d) } + adjacent.map { Point(it.x - 1, it.y, it.z, it.d) } + adjacent
    adjacent = adjacent.map { Point(it.x, it.y + 1, it.z, it.d) } + adjacent.map { Point(it.x, it.y - 1, it.z, it.d) } + adjacent
    adjacent = adjacent.map { Point(it.x, it.y, it.z + 1, it.d) } + adjacent.map { Point(it.x, it.y, it.z - 1, it.d) } + adjacent
    return adjacent.filter { it != p }
}

fun getAdjacent4(p: Point): List<Point> {
    var adjacent: List<Point> = listOf(p)
    adjacent = adjacent.map { Point(it.x + 1, it.y, it.z, it.d) } + adjacent.map { Point(it.x - 1, it.y, it.z, it.d) } + adjacent
    adjacent = adjacent.map { Point(it.x, it.y + 1, it.z, it.d) } + adjacent.map { Point(it.x, it.y - 1, it.z, it.d) } + adjacent
    adjacent = adjacent.map { Point(it.x, it.y, it.z + 1, it.d) } + adjacent.map { Point(it.x, it.y, it.z - 1, it.d) } + adjacent
    adjacent = adjacent.map { Point(it.x, it.y, it.z, it.d + 1) } + adjacent.map { Point(it.x, it.y, it.z, it.d - 1) } + adjacent
    return adjacent.filter { it != p }
}

fun simulateGame(input: List<String>, getAdj: (Point) -> List<Point>): Int {

    val active: MutableSet<Point> = mutableSetOf()
    val inactive: MutableSet<Point> = mutableSetOf()
    for (i in input.indices) {
        for (j in input[0].indices) {
            if (input[i][j] == '#') active.add(Point(i, j, 0))
            else {
                inactive.add(Point(i, j, 0))
            }
        }
    }
    val neighbours: MutableMap<Point, Int> = mutableMapOf()
    for (p in inactive) neighbours[p] = 0
    for (p in active) neighbours[p] = 0
    for (i in 1..6) {
        for (p in active) {
            val adjacent = getAdj(p)
            adjacent.forEach {
                if (it in neighbours) {
                    neighbours[it] = neighbours[it]!! + 1
                } else {
                    neighbours[it] = 1
                    inactive.add(it)
                }
            }
        }
        for (p in neighbours.keys) {
            if (neighbours[p] == 3 && p in inactive) {
                inactive.remove(p)
                active.add(p)
            }
            if (neighbours[p] !in 2..3 && p in active) {
                active.remove(p)
                inactive.add(p)
            }
        }
        for (p in inactive) neighbours[p] = 0
        for (p in active) neighbours[p] = 0
    }
    return active.size
}

fun main() {
    val input = ReadInput("input17.txt").readListOfStr()
    println(simulateGame(input, ::getAdjacent4))
}
