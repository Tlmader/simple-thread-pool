package csci4401.service;

import java.io.PrintWriter;

/**
 * Example test code; lacks timing. Use as needed.
 */
public class MatrixDeterminantServiceTest {

    private static PrintWriter writer;

    public static void main(String[] argv) throws Exception {
        writer = new PrintWriter("results.txt", "UTF-8");
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 6; j++) {
                sequentialTest(i * 10, j);
                parallelTest(i * 10r, j);
            }
        }
        writer.close();
    }

    /**
     * Launches the workers one after the other.
     */
    static private void sequentialTest(int mSize, int iterations) throws Exception {
        MatrixDeterminantServicePool servicePool = new MatrixDeterminantServicePool(5, 10);
        String threadResults = "";
        int max = 8;
        System.out.println("BEGIN Sequential Test | iterations: " + iterations + " | mSize: " + mSize);
        Long start = System.currentTimeMillis();
        for (int i = 0; i < max; i++) {
            servicePool.addRequest(new MatrixDeterminantParameters(mSize, iterations));
            String response = ("Thread " + i + ": " + servicePool.getResponse() + " ms");
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
    static private void parallelTest(int mSize, int iterations) throws Exception {
        MatrixDeterminantServicePool servicePool = new MatrixDeterminantServicePool(5, 10);
        String threadResults = "";
        int max = 8;
        System.out.println("BEGIN Parallel Test | iterations: " + iterations + " | mSize: " + mSize);
        Long start = System.currentTimeMillis();
        for (int i = 0; i < max; i++) {
            servicePool.addRequest(new MatrixDeterminantParameters(mSize, iterations));
        }
        for (int i = 0; i < max; i++) {
            String response = ("Thread " + i + ": " + servicePool.getResponse() + " ms");
            System.out.println(response);
            threadResults += (response + "\n");
        }
        Long end = System.currentTimeMillis();
        System.out.println("END Parallel Test | iterations: " + iterations + " | mSize: " + mSize + " | Result: " + (end - start) + " ms");
        writer.append("Parallel Test, iterations: " + iterations + ", mSize: " + mSize + ", Result: " + (end - start) + " ms\n");
        writer.append(threadResults);
    }
}
