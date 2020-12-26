import java.math.BigInteger

const val p1 = 13316116
const val p2 = 13651422

fun findLoopSize(subject: Int, public: Int): Int {
    var loop = 0
    var curr = 1.toBigInteger()
    val target = public.toBigInteger()
    val bigSubject = subject.toBigInteger()
    while (true) {
        curr *= bigSubject
        curr %= 20201227.toBigInteger()
        loop += 1
        if(curr == target) return loop
    }
}

fun makeEncryption(public: Int, loop: Int): BigInteger {
    var curr = 1.toBigInteger()
    repeat (loop) {
        curr *= public.toBigInteger()
        curr %= 20201227.toBigInteger()
    }
    return curr
}

fun findEncryptionKey(): BigInteger {
    val loop1 = findLoopSize(7, p1)
    val loop2 = findLoopSize(7, p2)
    val encryption = makeEncryption(p1, loop2)
    return encryption
}

fun main() {
    println(findEncryptionKey())
}