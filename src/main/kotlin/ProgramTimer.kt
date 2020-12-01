class ProgramTimer(val start: Long = System.nanoTime()) {
    fun endTimer() = println("Time: ${(System.nanoTime() - start) * 0.000001}ms")
}