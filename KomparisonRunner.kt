import kotlin.time.Duration
import kotlin.time.measureTime
import kotlinx.coroutines.*

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

    @Suppress("unused")
    fun fibonacci(n: Long): Long {
        if (n <= 2)
            return 1

        return fibonacci(n-2) + fibonacci(n-1)
    }

    fun waitCoroutine(n: Long): Long = runBlocking {
        var totalTimeWaited = 0L
        val jobs = ArrayList<Deferred<Long>>()
        repeat(n.toInt()) {
            val timeWaited = async {
                wait(100L)
            }
            jobs.add(timeWaited)
        }

        for (job in jobs) {
            totalTimeWaited += job.await()
        }

        totalTimeWaited
    }

    private suspend fun wait(timeMs: Long): Long {
        delay(timeMs)
        return timeMs
    }

    fun fibonacciCoroutine(n: Long): Long = runBlocking {
        var totalFibSum = 0L
        val jobs = ArrayList<Deferred<Long>>()
        repeat(n.toInt()) {
            val result = async {
                fibonacci(n)
            }
            jobs.add(result)
        }

        for (job in jobs) {
            totalFibSum += job.await()
        }

        totalFibSum
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
        KomparisonRunner::fibonacci.name to 35L,
        KomparisonRunner::waitCoroutine.name to 100_000L,
        KomparisonRunner::fibonacciCoroutine.name to 32L,
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
    val testFunction = runner::fibonacciCoroutine
    runTest(testFunction.name, testFunction)
}