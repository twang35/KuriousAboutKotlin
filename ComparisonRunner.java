public class ComparisonRunner {
    public static void main(String[] args) {
        System.out.println("comparison runner");

        long start = System.currentTimeMillis();
        long sum = ComparisonRunner.forLoops(1_000_000_000);
        long end = System.currentTimeMillis();
        System.out.println(sum);
        System.out.printf("forLoops: %d ms%n", end-start);
    }

    private static long forLoops(long n) {
        long sum = 0;

        for (int i = 0; i < n; i += 1) {
            sum += i;
        }

        return sum;
    }
}
