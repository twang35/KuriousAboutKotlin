import java.util.ArrayList;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ComparisonRunner {
    public static class LinkedList {
        LinkedList next;
        int value;
    }

    Map<String, Integer> nMappings = Map.of(
        "forLoops", 1_000_000_000,
        "addFirst", 100_000,
        "addLast", 20_000_000,
        "generateLinkedListChain", 100_000_000,
        "fibonacci", 35,
        "waitExecutorService", 100_000,
        "fibonacciExecutorService", 42
    );

    public static void main(String[] args) throws Exception {
        System.out.println("comparison runner");

        ComparisonRunner runner = new ComparisonRunner();

        Method testMethod = ComparisonRunner.class.getDeclaredMethod("fibonacciExecutorService", long.class);

        runner.runTest(testMethod);
    }

    private void runTest(Method method) throws Exception{
        int repeats = 10;
        ArrayList<Long> times = new ArrayList<>();
        Object[] parameters = new Object[1];
        int n = this.nMappings.get(method.getName());

        for (int i = 0; i < repeats; i++) {
            parameters[0] = n + i;
            long start = System.currentTimeMillis();
            long sum = (long) method.invoke(this, parameters);
            long end = System.currentTimeMillis();
            System.out.println(sum);
            System.out.printf("%s ms\n", end-start);
            times.add(end-start);
        }
        System.out.printf("%s times (ms): \n%s\n", method.getName(), times);
    }

    @SuppressWarnings("unused")
    private long forLoops(long n) {
        long sum = 0;

        for (int i = 0; i < n; i += 1) {
            sum += i;
        }

        return sum;
    }

    @SuppressWarnings("unused")
    private long addFirst(long n) {
        ArrayList<Long> testList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            testList.addFirst(n);
        }

        return testList.getLast();
    }

    @SuppressWarnings("unused")
    private long addLast(long n) {
        ArrayList<Long> testList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            testList.addLast(n);
        }

        return testList.getLast();
    }

    @SuppressWarnings("unused")
    private long generateLinkedListChain(long n) {
        LinkedList cur = new LinkedList();
        cur.value = 0;
        for (int i = 0; i < n; i++) {
            cur.next = new LinkedList();
            cur = cur.next;
            cur.value = i;
        }

        return cur.value;
    }

    @SuppressWarnings("unused")
    private long fibonacci(long n) {
        if (n <= 2)
            return 1;

        return fibonacci(n-2) + fibonacci(n-1);
    }

    @SuppressWarnings("unused")
    private long waitExecutorService(long n) throws Exception {
        long totalTimeWaited = 0;
        ArrayList<Future<Long>> jobs = new ArrayList<>();
        Callable<Long> c = this::waitTest;

        try (ExecutorService executorService = Executors.newCachedThreadPool()) {
            for (int i = 0; i < n; i++) {
                Future<Long> job = executorService.submit(c);
                jobs.add(job);
            }

            for (Future<Long> j : jobs) {
                totalTimeWaited += j.get();
            }
        }

        return totalTimeWaited;
    }

    private long waitTest() throws InterruptedException {
        Thread.sleep(100L);
        return 100L;
    }

    @SuppressWarnings("unused")
    private long fibonacciExecutorService(long n) throws Exception {
        long totalTimeWaited = 0;
        ArrayList<Future<Long>> jobs = new ArrayList<>();
        Callable<Long> c = () -> fibonacci(n);

        try (ExecutorService executorService = Executors.newCachedThreadPool()) {
            for (int i = 0; i < n; i++) {
                Future<Long> job = executorService.submit(c);
                jobs.add(job);
            }

            for (Future<Long> j : jobs) {
                totalTimeWaited += j.get();
            }
        }

        return totalTimeWaited;
    }
}
