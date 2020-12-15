enum class Seat {
    TAKEN, EMPTY, FLOOR
}

fun getNextSeat(i: Int, j: Int, seats: List<List<Seat>>, stepI: Int, stepJ: Int): Seat {
    var currI = i + stepI
    var currJ = j + stepJ
    while (currI in seats.indices && currJ in seats[0].indices) {
        if (seats[currI][currJ] == Seat.TAKEN) return Seat.TAKEN
        if (seats[currI][currJ] == Seat.EMPTY) return Seat.EMPTY
        currI += stepI
        currJ += stepJ
    }
    return Seat.FLOOR
}

fun getAdjacent1(i: Int, j: Int, seats: List<List<Seat>>): List<Seat> = listOfNotNull(seats[i].getOrNull(j - 1), seats[i].getOrNull(j + 1), seats.getOrNull(i + 1)?.get(j), seats.getOrNull(i - 1)?.get(j), seats.getOrNull(i - 1)?.getOrNull(j - 1), seats.getOrNull(i + 1)?.getOrNull(j - 1), seats.getOrNull(i + 1)?.getOrNull(j + 1), seats.getOrNull(i - 1)?.getOrNull(j + 1))

fun getAdjacent2(i: Int, j: Int, seats: List<List<Seat>>): List<Seat> = listOfNotNull(getNextSeat(i, j, seats, 1, 0), getNextSeat(i, j, seats, -1, 0), getNextSeat(i, j, seats, 0, 1), getNextSeat(i, j, seats, 0, -1), getNextSeat(i, j, seats, 1, 1), getNextSeat(i, j, seats, 1, -1), getNextSeat(i, j, seats, -1, 1), getNextSeat(i, j, seats, -1, -1))

fun updateGrid(seats: List<List<Seat>>, adjacentFunc: (Int, Int, List<List<Seat>>) -> List<Seat>, tolerance: Int): List<List<Seat>> {
    val output = Array(seats.size) { Array(seats[0].size) { Seat.EMPTY } }
    for (i in seats.indices) {
        for (j in seats[0].indices) {
            val adjacent = adjacentFunc(i, j, seats)
            if (seats[i][j] == Seat.EMPTY && adjacent.count { it == Seat.TAKEN } == 0) {
                output[i][j] = Seat.TAKEN
            } else if (seats[i][j] == Seat.TAKEN && adjacent.count { it == Seat.TAKEN } >= tolerance) output[i][j] = Seat.EMPTY
            else {
                output[i][j] = seats[i][j]
            }
        }
    }
    return output.map { it.toList() }.toList()
}

fun part1(seats: List<List<Seat>>): Int {
    var currSeats = seats
    var nextSeats = updateGrid(currSeats, ::getAdjacent1, 4)
    while (currSeats != nextSeats) {
        currSeats = nextSeats
        nextSeats = updateGrid(currSeats, ::getAdjacent1, 4)
    }
    return currSeats.sumBy { it.count { s -> s == Seat.TAKEN } }
}

fun part2(seats: List<List<Seat>>): Int {
    var currSeats = seats
    var nextSeats = updateGrid(currSeats, ::getAdjacent2, 5)
    var count = 0
    for (i in 0..100) {
        currSeats = nextSeats
        nextSeats = updateGrid(currSeats, ::getAdjacent2, 5)
        count += 1
    }
    return currSeats.sumBy { it.count { s -> s == Seat.TAKEN } }
}

fun main() {
    val input = ReadInput("input11.txt").readListOfStr().map { it.toCharArray() }
    val seats = input.map { it.map { c -> if (c == 'L') Seat.EMPTY else if (c == '#') Seat.TAKEN else Seat.FLOOR } }
    println(part2(seats))
}
