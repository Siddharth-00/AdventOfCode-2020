import java.io.File

class ReadInput(val path: String) {
    fun readListOfInts() = File(path).readLines().map { it.toInt() }.toIntArray()
}

class ProgramTimer(val start: Long = System.nanoTime()) {
    fun endTimer() = println("Time: ${(System.nanoTime() - start) * 0.000001}ms")
}