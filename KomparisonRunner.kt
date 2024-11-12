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

fun main(args: Array<String>) {
    println("hello world")

    val n: Long = 1_000_000_000
    val runner = KomparisonRunner()
    var sum: Long
    val timeTaken = measureTime {
        sum = runner.forLoops(n)
    }
    println(sum)
    println("forLoops: $timeTaken")
}
