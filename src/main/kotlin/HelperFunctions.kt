class ReadInput(private val path: String) {
    private fun readLines(): List<String> = javaClass.classLoader.getResource(path).readText().split("\n").dropLast(1)

    fun readListOfInts() = readLines().map { it.toInt() }.toIntArray()

    fun readListOfStr() = readLines()

    fun readGroupsStr() =  javaClass.classLoader.getResource(path).readText().split("\n\n").dropLast(1)

    fun readGrid() = readLines().map { it.toCharArray() }.toTypedArray()
}

class ProgramTimer(private val start: Long = System.nanoTime()) {
    fun endTimer() = println("Time: ${"%.2f".format((System.nanoTime() - start) * 0.000001)}ms")
}

// Passport Conditions
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

fun hcl(s: String): Boolean = s.matches("#[0-9a-f]{6}".toRegex())
fun ecl(s: String): Boolean = s in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
fun pid(s: String): Boolean = s.matches("\\d{9}".toRegex())
// End of Passport Conditions
