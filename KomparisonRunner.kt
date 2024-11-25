import kotlin.time.Duration
import kotlin.time.measureTime

class KomparisonRunner {
    @Suppress("unused")
    fun forLoops(n: Long): Long {
        var sum: Long = 0

        for (i in 0..<n) {
            sum += i
        }

        return sum
    }

    @Suppress("unused")
    fun addFirst(n: Long): Long {
        val arraylist = ArrayList<Long>()

        for (i in 1..<n) {
            arraylist.addFirst(n)
        }

        return arraylist.last
    }

    @Suppress("unused")
    fun addLast(n: Long): Long {
        val arraylist = ArrayList<Long>()

        for (i in 1..<n) {
            arraylist.addLast(n)
        }

        return arraylist.last
    }

    @Suppress("unused")
    fun generateLinkedListChain(n: Long): Long {
        var cur = LinkedList()
        cur.value = 0
        for (i in 0 until n) {
            cur.next = LinkedList()
            cur = cur.next!!
            cur.value = i.toInt()
        }

        return cur.value.toLong()
    }
}

class LinkedList {
    var next: LinkedList? = null
    var value: Int = 0
}

private fun runTest(functionName: String, testFunction: (Long) -> Long) {
    val nMappings: HashMap<String, Long> = hashMapOf(
        KomparisonRunner::addLast.name to 20_000_000L,
        KomparisonRunner::addFirst.name to 100_000L,
        KomparisonRunner::forLoops.name to 1_000_000_000L,
        KomparisonRunner::generateLinkedListChain.name to 100_000_000L,
        )

    val repeats = 10
    val n = nMappings[functionName]
    var result: Long
    val times  = mutableListOf<Duration>()
    for (i in 0..<repeats) {
        val timeTaken = measureTime {
            result = testFunction(n!! + i)
        }

        println(result)
        println("time: $timeTaken")
        times.addLast(timeTaken)
    }

    println("$functionName times: \n$times")
}

fun main() {
    val runner = KomparisonRunner()
    val testFunction = runner::generateLinkedListChain
    runTest(testFunction.name, testFunction)
}