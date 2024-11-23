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
}

fun main() {
    val repeats = 10
    val n: Long = 100_000
    val runner = KomparisonRunner()
    var sum: Long
    val times  = mutableListOf<Duration>()
    for (i in 0..<repeats) {
        val timeTaken = measureTime {
            sum = runner.addFirst(n + i)
        }
        println("sum: $sum")
        println("addFirst time: $timeTaken")
        times.addLast(timeTaken)
    }

    println(times)
}
