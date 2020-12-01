
class Solution() {
    fun findPair(input: IntArray, target: Int): Int {
        val storedNums: MutableMap<Int, Boolean> = mutableMapOf()
        for (n in input) {
            if (target >= n && storedNums.getOrDefault(target - n, false)) {
                return (target - n) * n
            }
            storedNums[n] = true
        }
        return 0
    }

    fun findTriplet(input: IntArray, target: Int): Int {
        for (i in 2 until input.size) {
            if (input[i] > target) {
                return 0
            }
            if (input[i] != input[i - 1]) {
                val pairSum = findPair(input.sliceArray(0 until i), target - input[i])
                if (pairSum != 0) return pairSum * input[i]
            }
        }
        return 0
    }
}



fun main() {
    val timer = ProgramTimer()
    val path = "/Users/siddharthsharma/Documents/GitHub/AdventOfCode-2020/src/main/resources/input1.txt"
    val input: IntArray = ReadInput(path).readListOfInts()
    val solution = Solution()
    println(solution.findTriplet(input, 2020))
    timer.endTimer()
}
