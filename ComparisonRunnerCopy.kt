class ComparisonRunnerCopy {
    private fun forLoops(n: Long): Long {
        var sum: Long = 0

        var i = 0
        while (i < n) {
            sum += i.toLong()
            i += 1
        }

        return sum
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println("comparison runner")

            val repeats = 10
            val times = ArrayList<Long>()

            val runner = ComparisonRunnerCopy()

            for (i in 0 until repeats) {
                val start = System.currentTimeMillis()
                val sum = runner.forLoops((1000000000 + i).toLong())
                val end = System.currentTimeMillis()
                println(sum)
                println(end - start)
                times.add(end - start)
            }
            System.out.printf("forLoops: %s\n", times)
        }
    }
}