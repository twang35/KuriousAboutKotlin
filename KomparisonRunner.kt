import kotlin.time.Duration
import kotlin.time.measureTime

class KomparisonRunner {
    fun forLoops(n: Long): Long {
        var sum: Long = 0

        for (i in 0..<n) {
            sum += i
        }

        return sum
    }
}

fun main() {
    val repeats = 10
    val n: Long = 1_000_000_000
    val runner = KomparisonRunner()
    var sum: Long
    val times  = mutableListOf<Duration>()
    for (i in 0..<repeats) {
        val timeTaken = measureTime {
            sum = runner.forLoops(n + i)
        }
        println("sum: $sum")
        println("forLoops time: $timeTaken")
        times.addLast(timeTaken)
    }

    println(times)
}
