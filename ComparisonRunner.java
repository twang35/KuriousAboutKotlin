import java.util.ArrayList;

public class ComparisonRunner {
    public static void main(String[] args) {
        System.out.println("comparison runner");

        int repeats = 2;
        ArrayList<Long> times = new ArrayList<>();

        for (int i = 0; i < repeats; i++) {
            long start = System.currentTimeMillis();
            long sum = ComparisonRunner.forLoops(1_000_000_000 + i);
            long end = System.currentTimeMillis();
            System.out.println(sum);
            System.out.println(end-start);
            times.add(end-start);
        }
        System.out.printf("forLoops: %s\n", times);
    }

    private static long forLoops(long n) {
        long sum = 0;

        for (int i = 0; i < n; i += 1) {
            sum += i;
        }

        return sum;
    }
}
