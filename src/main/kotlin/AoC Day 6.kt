fun sumAnyone(input: List<String>): Int = input.sumBy { it.split("\n").reduce { a, b -> a.toSet().union(b.toSet()).joinToString("") }.length }

fun sumEveryone(input: List<String>): Int = input.sumBy { it.split("\n").reduce { a, b -> a.toSet().intersect(b.toSet()).joinToString("") }.length }

fun main() {
    val input = ReadInput("input6.txt").readGroupsStr()
    println(sumEveryone(input))
}
