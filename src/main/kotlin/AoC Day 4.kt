fun byr(s: String): Boolean = s.toInt() in 1920..2002
fun iyr(s: String): Boolean = s.toInt() in 2010..2020
fun eyr(s: String): Boolean = s.toInt() in 2020..2030
fun hgt(s: String): Boolean {
    return when (s.takeLast(2)) {
        "cm" -> {
            s.dropLast(2).toInt() in 150..193
        }
        "in" -> {
            s.dropLast(2).toInt() in 59..76
        }
        else -> false
    }
}

fun hcl(s: String): Boolean {
    val regex = "#[0-9a-f]{6}".toRegex()
    return s.matches(regex)
}

fun ecl(s: String): Boolean = s in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
fun pid(s: String): Boolean {
    val regex = "\\d{9}".toRegex()
    return s.matches(regex)
}

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
