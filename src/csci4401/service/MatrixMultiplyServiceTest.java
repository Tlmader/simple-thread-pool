package csci4401.service;

import java.io.PrintWriter;

/**
 * Example test code; lacks timing. Use as needed.
 */
public class MatrixMultiplyServiceTest {

    private static PrintWriter writer;

    public static void main(String[] argv) throws Exception {
        int poolMax = Runtime.getRuntime().availableProcessors();
        runTests(new BalancedMMServicePool(0, poolMax));
        runTests(new ReusableMMServicePool(0, poolMax));
        runTests(new MatrixMultiplyServicePool(0, poolMax));
    }

    /**
     * Runs a sequential and parallel tests using different parameters.
     * @param pool a ServicePool
     * @throws Exception
     */
    static private void runTests(ServicePool pool) throws Exception {
        writer = new PrintWriter("results.txt", "UTF-8");
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 6; j++) {
                sequentialTest(i * 100, j, pool);
                parallelTest(i * 100, j, pool);
            }
        }
        writer.close();
    }

    /**
     * Launches the workers one after the other.
     */
    static private void sequentialTest(int mSize, int iterations, ServicePool pool) throws Exception {
        String threadResults = "";
        int max = 8;
        System.out.println("BEGIN Sequential Test | iterations: " + iterations + " | mSize: " + mSize +
                " | pool: " + pool.getClass().getSimpleName());
        Long start = System.currentTimeMillis();
        for (int i = 0; i < max; i++) {
            pool.addRequest(new MatrixMultiplyParameters(mSize, iterations));
            String response = ("Thread " + i + ": " + pool.getResponse() + " ms");
            System.out.println(response);
            threadResults += (response + "\n");
        }
        Long end = System.currentTimeMillis();
        System.out.println("END Sequential Test | iterations: " + iterations + " | mSize: " + mSize + " | Result: " + (end - start) + " ms");
        writer.append("Sequential Test, iterations: " + iterations + ", mSize: " + mSize + ", Result: " + (end - start) + " ms\n");
        writer.append(threadResults);
    }

    /**
     * Launches the workers in parallel.
     */
    static private void parallelTest(int mSize, int iterations, ServicePool pool) throws Exception {
        String threadResults = "";
        int max = 8;
        System.out.println("BEGIN Parallel Test | iterations: " + iterations + " | mSize: " + mSize +
                " | pool: " + pool.getClass().getSimpleName());
        Long start = System.currentTimeMillis();
        for (int i = 0; i < max; i++) {
            pool.addRequest(new MatrixMultiplyParameters(mSize, iterations));
        }
        for (int i = 0; i < max; i++) {
            String response = ("Thread " + i + ": " + pool.getResponse() + " ms");
            System.out.println(response);
            threadResults += (response + "\n");
        }
        Long end = System.currentTimeMillis();
        System.out.println("END Parallel Test | iterations: " + iterations + " | mSize: " + mSize + " | Result: " + (end - start) + " ms");
        writer.append("Parallel Test, iterations: " + iterations + ", mSize: " + mSize + ", Result: " + (end - start) + " ms\n");
        writer.append(threadResults);
    }
}
