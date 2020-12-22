
fun makeList(input: List<String>): MutableMap<String, Set<String>> {
    val map: MutableMap<String, Set<String>> = mutableMapOf()
    input.forEach { list ->
        val allergens = list.substringAfter("(contains ").substringBefore(")").split(", ")
        val ingredients = list.substringBefore(" (").split(" ").toSet()
        allergens.forEach { a ->
            if (a in map) map[a] = map[a]!!.intersect(ingredients)
            else map[a] = ingredients
        }
    }
    val removalList = mutableSetOf<String>()
    for (ingredient in map.values) if (ingredient.size == 1) removalList.add(ingredient.first())
    outer@while (true) {
        for ((allergen, ingredient) in map) {
            if (ingredient.size > 1 && ingredient.intersect(removalList).isNotEmpty()) {
                map[allergen] = map[allergen]!!.subtract(ingredient.intersect(removalList))
                if (map[allergen]!!.size == 1) {
                    removalList.add(map[allergen]!!.first())
                    continue@outer
                }
            }
        }
        break
    }
    return map
}

fun findNoAllergens(input: List<String>, map: MutableMap<String, Set<String>>): Int {
    val marked = map.values.reduce { acc, set -> acc.union(set) }
    val unmarked = mutableSetOf<String>()
    input.forEach { list ->
        val ingredients = list.substringBefore(" (").split(" ").toSet()
        unmarked.addAll(ingredients.minus(ingredients.intersect(marked)))
    }
    return input.sumBy { it.substringBefore(" (").split(" ").toSet().intersect(unmarked).size }
}

fun makeCanonList(map: MutableMap<String, Set<String>>): String = map.keys.sorted().joinToString(",") { map[it]!!.first() }

fun main() {
    val input = ReadInput("input21.txt").readListOfStr()
    println(makeCanonList(makeList(input)))
}
