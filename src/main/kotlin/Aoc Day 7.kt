fun buildRMap(input: List<String>): MutableMap<String, Map<String, Int>> {
    val map: MutableMap<String, Map<String, Int>> = mutableMapOf()
    val regex1 = "([\\w ]+) bags contain".toRegex()
    val regex2 = "(\\d+) ([\\w ]+) bag".toRegex()
    input.forEach {
        val rootCol = regex1.find(it)!!.destructured.component1()
        map[rootCol] = regex2.findAll(it).map { m -> m.destructured.component2() to m.destructured.component1().toInt() }.toMap()
    }
    return map
}

fun checkValid(map: MutableMap<String, Map<String, Int>>, colour: String, target: String): Boolean {
    if (map[colour].isNullOrEmpty()) return false
    return map[colour]!!.keys.any { it == target } || map[colour]!!.keys.any { checkValid(map, it, target) }
}

fun findTarget(map: MutableMap<String, Map<String, Int>>, target: String = "shiny gold") = map.keys.count { checkValid(map, it, target) }

fun countBags(map: MutableMap<String, Map<String, Int>>, colour: String): Int = map[colour]!!.keys.sumBy { map[colour]!![it]!! * (countBags(map, it) + 1) }

fun main() {
    val input = ReadInput("input7.txt").readListOfStr()
    val map = buildRMap(input)
    println(findTarget(map))
    println(countBags(map, "shiny gold"))
}
