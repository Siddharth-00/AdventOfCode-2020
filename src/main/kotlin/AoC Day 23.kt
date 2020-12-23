import java.lang.StringBuilder

const val input = "916438275"

class CircularLinkedList(val id: Int, var next: CircularLinkedList? = null) {
    fun traverse(n: Int): CircularLinkedList {
        var curr = this
        for (i in 1..n) {
            curr = curr.next !!
        }
        return curr
    }

    fun nextLowest(tail: CircularLinkedList, extra: Int): Int {
        var currTail: CircularLinkedList? = tail
        val removed = listOf(currTail !!.id, currTail.next !!.id, currTail !!.next !!.next !!.id)
        var currTarget = this.id - 1
        if (currTarget == 0) currTarget = extra
        while (currTarget in removed) {
            currTarget -= 1
            if (currTarget == 0) currTarget = extra
        }
        return currTarget
    }

    fun printList(): String {
        var curr = this.next
        var output = StringBuilder()
        while (curr != this) {
            output.append(curr !!.id)
            curr = curr.next
        }
        return this.id.toString() + output.toString()
    }

    override fun toString(): String {
        return id.toString()
    }

    override fun equals(other: Any?): Boolean = other is CircularLinkedList && other.id == this.id
}

fun simulate(times: Int, extra: Int) {
    val nodes = Array(1 + extra) { CircularLinkedList(0) }
    val head = CircularLinkedList(Character.digit(input[0], 10))
    var curr = head
    for (i in 1..8) {
        val next = CircularLinkedList(Character.digit(input[i], 10))
        curr.next = next
        curr = next
        nodes[Character.digit(input[i], 10)] = next
    }
    for (j in 10..extra) {
        val next = CircularLinkedList(j)
        curr.next = next
        curr = next
        nodes[j] = next
    }
    curr.next = head
    curr = head
    nodes[Character.digit(input[0], 10)] = head
    repeat(times) {
        val sliceHead = curr.next
        val sliceTail = curr.traverse(3)
        curr.next = sliceTail.next
        sliceTail.next = null
        val destination = nodes[curr.nextLowest(sliceHead !!, extra)]
        sliceTail.next = destination !!.next
        destination.next = sliceHead
        curr = curr.next !!
    }
    println(nodes[1].next !!.id.toBigInteger() * nodes[1].next !!.next !!.id.toBigInteger())
}

fun main() {
    simulate(10000000, 1000000)
}
