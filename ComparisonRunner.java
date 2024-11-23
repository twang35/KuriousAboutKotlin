import java.util.ArrayList;
import java.lang.reflect.Method;
import java.util.Map;

public class ComparisonRunner {
    Map<String, Integer> nMappings = Map.of(
        "forLoops", 1_000_000_000,
        "addFirst", 100_000,
        "addLast", 20_000_000
    );

    public static void main(String[] args) throws Exception {
        System.out.println("comparison runner");

        ComparisonRunner runner = new ComparisonRunner();

        Method testMethod = ComparisonRunner.class.getDeclaredMethod("addLast", long.class);

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
            System.out.println(end-start);
            times.add(end-start);
        }
        System.out.printf("%s: %s (ms)\n", method.getName(), times);
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
}
