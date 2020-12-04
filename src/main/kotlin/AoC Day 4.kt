fun processPairs(): MutableList<Map<String, String>> {
    val output: MutableList<Map<String, String>> = mutableListOf()
    var currPassport: MutableMap<String, String> = mutableMapOf()
    ReadInput("input4.txt").readListOfStr().forEach { pairs ->
        when (pairs) {
            "" -> {
                output.add(currPassport)
                currPassport = mutableMapOf()
            }
            else -> {
                val regex = "(\\w{3}):([\\w#]+)".toRegex()
                regex.findAll(pairs).forEach { currPassport[it.destructured.component1()] = it.destructured.component2() }
            }
        }
    }
    return output
}

val conditions: Map<String, (String) -> Boolean> = mapOf(Pair("byr", ::byr), Pair("iyr", ::iyr), Pair("eyr", ::eyr), Pair("hgt", ::hgt), Pair("hcl", ::hcl), Pair("ecl", ::ecl), Pair("pid", ::pid))

fun countValid1(passports: MutableList<Map<String, String>>) = passports.count { it.keys.size == 8 || (it.keys.size == 7 && "cid" !in it.keys) }

fun countValid2(passports: MutableList<Map<String, String>>): Int = passports.count { passport ->
    conditions.keys.all { key ->
        key in passport.keys && (conditions[key] ?: error(""))(passport[key] ?: error(""))
    }
}

fun main() {
    println(countValid2(processPairs()))
}
