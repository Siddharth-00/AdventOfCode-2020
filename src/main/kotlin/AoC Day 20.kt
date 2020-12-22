import java.math.BigInteger
import java.util.*

class Tile(var tile: List<String>, val id: Int) {
    var edges = mutableListOf<String>()
    var top: Tile? = null
    var right: Tile? = null
    var bot: Tile? = null
    var left: Tile? = null
    init {
        edges = mutableListOf(tile[0], tile.map { it.last() }.joinToString(""), tile.last().reversed(), tile.map { it.first() }.joinToString("").reversed())
    }

    fun flipSide() {
        tile = tile.map { it.reversed() }
        edges = mutableListOf(tile[0], tile.map { it.last() }.joinToString(""), tile.last(), tile.map { it.first() }.joinToString(""))
    }

    fun flipUp() {
        tile = tile.reversed()
        edges = mutableListOf(tile[0], tile.map { it.last() }.joinToString(""), tile.last(), tile.map { it.first() }.joinToString(""))
    }

    fun rotate() {
        tile = List(tile[0].length) { i -> tile.map { it[i] }.joinToString("") }.map { it.reversed() }
        edges = mutableListOf(tile[0], tile.map { it.last() }.joinToString(""), tile.last(), tile.map { it.first() }.joinToString(""))
    }

    fun removeBorder() {
        tile = tile.subList(1, tile.size).map { it.substring(1, it.length) }
    }

    fun setConnection(i: Int, tile: Tile) {
        when (i) {
            0 -> top = tile
            1 -> right = tile
            2 -> bot = tile
            3 -> left = tile
        }
    }

    fun getConnection(i: Int): Tile? {
        return when (i) {
            0 -> top
            1 -> right
            2 -> bot
            3 -> left
            else -> error("")
        }
    }

    override fun equals(other: Any?): Boolean = other is Tile && other.id == this.id
    override fun toString(): String {
        return tile.joinToString("\n")
    }
}

fun makeTiles(input: List<String>): List<Tile> {
    val regex = "Tile (\\d+):".toRegex()
    return input.map { Tile(it.substringAfter(":\n").split("\n"), regex.find(it.substringBefore("\n"))!!.destructured.component1().toInt()) }
}

fun findCorners(tiles: List<Tile>): BigInteger {
    val edges: MutableMap<String, Int> = mutableMapOf()
    tiles.forEach {
        it.edges.forEach { e ->
            if (e in edges) {
                edges[e] = edges[e]!! + 1
            } else if (e.reversed() in edges) {
                edges[e.reversed()] = edges[e.reversed()]!! + 1
            } else {
                edges[e.reversed()] = 1
            }
        }
    }
    println(tiles.filter { it.edges.count { e -> edges.getOrDefault(e, 2) > 1 && edges.getOrDefault(e.reversed(), 2) > 1 } == 2 }.map { it.id.toBigInteger() })
    return tiles.filter { it.edges.count { e -> edges.getOrDefault(e, 2) > 1 && edges.getOrDefault(e.reversed(), 2) > 1 } == 2 }.map { it.id.toBigInteger() }.reduce { a, b -> a * b }
}

fun findPair(tiles: List<Tile>, edge: String, curr: Tile, covered: MutableSet<Tile>): Tile? {
    for (it in tiles) {
        if (it !in covered && it.edges.contains(edge) && it != curr) return it
        it.rotate()
        it.rotate()
        if (it !in covered && it.edges.contains(edge) && it != curr) return it
        it.rotate()
        it.rotate()
        if (it !in covered && it.edges.contains(edge) && it != curr) return it
        it.flipUp()
        if (it !in covered && it.edges.contains(edge) && it != curr) return it
        it.flipUp()
        it.flipSide()
        if (it !in covered && it.edges.contains(edge) && it != curr) return it
        it.flipSide()
    }
    return null
}

fun opposite(a: Int): Int {
    return when (a) {
        0 -> 2
        1 -> 3
        2 -> 0
        3 -> 1
        else -> error("Cannot find opposite of $a")
    }
}

fun makePuzzle(tiles: List<Tile>) {
    val covered = mutableListOf<Tile>()
    val remaining = tiles.toMutableList()

    val tileStack = Stack<Tile>()
    tileStack.push(tiles.first())

    // first pass: assign connections to each tile
    while (tileStack.isNotEmpty()) {
        val currentTile = tileStack.pop()
        remaining -= currentTile
        val currentBorders = currentTile.edges

        remaining.forEach { tile ->
            tile.edges.forEachIndexed { otherI, edge ->
                if (edge in currentBorders) {
                    val currI = currentBorders.indexOf(edge)
                    val rotations = ((currI + 4) - opposite(otherI) % 4)
                    if (currentTile.getConnection(currI) == null) {
                        repeat(rotations) { tile.rotate() }
                        if (currI == 0 || currI == 2) tile.flipSide()
                        else tile.flipUp()
                        currentTile.setConnection(currI, tile)
                        tile.setConnection(opposite(currI), currentTile)
                        tileStack.push(tile)
                    }
                } else if (edge.reversed() in currentBorders) {
                    val currI = currentBorders.indexOf(edge.reversed())
                    val rotations = ((currI + 4) - opposite(otherI)) % 4
                    if (currentTile.getConnection(currI) == null) {
                        repeat(rotations) { tile.rotate() }
                        currentTile.setConnection(currI, tile)
                        tile.setConnection(opposite(currI), currentTile)
                        tileStack.push(tile)
                    }
                }
            }
        }
        covered += currentTile
    }
    println(tiles.filter { listOf(it.top, it.bot, it.left, it.right).filterNotNull().size == 2 }.map { listOf(it.top, it.bot, it.left, it.right).map { it?.id } })
}

fun main() {
    val input = ReadInput("input20.txt").readGroupsStr()
    makePuzzle(makeTiles(input))
    println(findCorners(makeTiles(input)))
}
