class ReadInput(val path: String) {
    private fun readLines(): List<String> = javaClass.classLoader.getResource(path).readText().split("\n").dropLast(1)

    fun readListOfInts() = readLines().map { it.toInt() }.toIntArray()

    fun readListOfStr() = readLines()

    fun readGrid() = readLines().map { it.toCharArray() }.toTypedArray()
}

class ProgramTimer(private val start: Long = System.nanoTime()) {
    fun endTimer() = println("Time: ${"%.2f".format((System.nanoTime() - start) * 0.000001)}ms")
}
