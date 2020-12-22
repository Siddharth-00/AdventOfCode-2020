import java.util.*

fun makeQueues(input: List<String>): Pair<Queue<Int>, Queue<Int>> {
    val player1: Queue<Int> = LinkedList<Int>()
    val player2: Queue<Int> = LinkedList<Int>()
    for (n in input[0].substringAfter("Player 1:\n").split("\n")) player1.add(n.toInt())
    for (n in input[1].substringAfter("Player 2:\n").split("\n")) player2.add(n.toInt())
    return player1 to player2
}

fun playGame1(players: Pair<Queue<Int>, Queue<Int>>): Int {
    val player1 = players.first
    val player2 = players.second
    while (player1.isNotEmpty() && player2.isNotEmpty()) {
        val card1 = player1.poll()
        val card2 = player2.poll()
        if (card1 > card2) {
            player1.offer(card1)
            player1.offer(card2)
        } else {
            player2.offer(card2)
            player2.offer(card1)
        }
    }
    return if (player1.isEmpty()) {
        var output = 0
        for (i in player2.size downTo 1) output += i * player2.poll()
        output
    } else {
        var output = 0
        for (i in player1.size downTo 1) output += i * player1.poll()
        output
    }
}

fun playGame2(players: Pair<Queue<Int>, Queue<Int>>): Pair<Boolean, Int> {
    val player1 = players.first
    val player2 = players.second
    val previousStates = mutableSetOf<Pair<Queue<Int>, Queue<Int>>>()
    var winner = true
    while (true) {
        if (player1 to player2 in previousStates) {
            break
        }
        previousStates.add(player1 to player2)
        if (player1.isEmpty()) {
            winner = false
            break
        } else if (player2.isEmpty()) break
        val card1 = player1.poll()
        val card2 = player2.poll()
        if (card1 > player1.size || card2 > player2.size) {
            if (card1 > card2) {
                player1.offer(card1)
                player1.offer(card2)
            } else {
                player2.offer(card2)
                player2.offer(card1)
            }
        } else {
            val newPlayer1: Queue<Int> = LinkedList<Int>()
            player1.forEachIndexed { i, c -> if (i < card1) newPlayer1.offer(c) }
            val newPlayer2: Queue<Int> = LinkedList<Int>()
            player2.forEachIndexed { i, c -> if (i < card2) newPlayer2.offer(c) }
            val roundWinner = playGame2(newPlayer1 to newPlayer2).first
            if (roundWinner) {
                player1.offer(card1)
                player1.offer(card2)
            } else {
                player2.offer(card2)
                player2.offer(card1)
            }
        }
    }
    var output = 0
    if (! winner) for (i in player2.size downTo 1) output += i * player2.poll()
    else for (i in player1.size downTo 1) output += i * player1.poll()
    return winner to output
}

fun main() {
    val input = ReadInput("input22.txt").readGroupsStr()
    println(playGame2(makeQueues(input)).second)
}
