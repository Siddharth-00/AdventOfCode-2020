import kotlin.math.*

class Position(var i: Int, var j: Int, var degrees: Int) {
    val commands = mapOf('N' to ::north, 'S' to ::south, 'E' to ::east, 'W' to ::west, 'F' to ::forward, 'L' to ::left, 'R' to ::right)
    fun turn(d: Int) = run { degrees += d }
    fun forward(d: Int) =
        run { i += (d * cos(degrees.toDouble() * PI / 180)).roundToInt(); j += (d * sin(degrees.toDouble() * PI / 180)).roundToInt() }
    fun north(d: Int) = run { j += d }
    fun south(d: Int) = run { j -= d }
    fun east(d: Int) = run { i += d }
    fun west(d: Int) = run { i -= d }
    fun distance(): Int = i.absoluteValue + j.absoluteValue
    fun right(d: Int) = run { turn(- 1 * d) }
    fun left(d: Int) = run { turn(d) }
    fun processCommand(s: String) {
        commands[s[0]] !!(s.substring(1).toInt())
    }
}

class Position2(var i: Int, var j: Int, var degrees: Int, var wayI: Int = 10, var wayJ: Int = 1) {
    val commands = mapOf('N' to ::north, 'S' to ::south, 'E' to ::east, 'W' to ::west, 'F' to ::forward, 'L' to ::leftWay, 'R' to ::rightWay)
    fun forward(d: Int) = run {
        val diffI = wayI - i
        val diffJ = wayJ - j
        i += diffI * d; j += diffJ * d; wayI += diffI * d; wayJ += diffJ * d
    }
    fun north(d: Int) = run { wayJ += d }
    fun south(d: Int) = run { wayJ -= d }
    fun east(d: Int) = run { wayI += d }
    fun west(d: Int) = run { wayI -= d }
    fun distance(): Int = i.absoluteValue + j.absoluteValue
    fun rightWay(d: Int) = run { rotateWay(d * - 1) }
    fun leftWay(d: Int) = run { rotateWay(d) }
    fun rotateWay(d: Int) {
        val newI = (wayI - i) * cos(d.toDouble() * PI / 180).roundToInt() - (wayJ - j) * sin(d.toDouble() * PI / 180).roundToInt() + i
        val newJ = (wayI - i) * sin(d.toDouble() * PI / 180).roundToInt() + (wayJ - j) * cos(d.toDouble() * PI / 180).roundToInt() + j
        wayI = newI
        wayJ = newJ
    }
    fun processCommand(s: String) {
        commands[s[0]] !!(s.substring(1).toInt())
    }
}


fun findDistance1(input: List<String>): Int {
    val position = Position(0, 0, 0)
    //println("(i, j): (${position.i}, ${position.j}) Degrees: ${position.degrees} ")
    input.forEach { position.processCommand(it) }
    return position.distance()
}

fun findDistance2(input: List<String>): Int {
    val position = Position2(0, 0, 0)
    //println("(i, j): (${position.i}, ${position.j}) Degrees: ${position.degrees} ")
    input.forEach { position.processCommand(it) }
    return position.distance()
}


fun main() {
    val input = ReadInput("input12.txt").readListOfStr()
    println(findDistance2(input))
}