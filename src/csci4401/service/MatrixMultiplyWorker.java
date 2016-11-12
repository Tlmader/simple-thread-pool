package csci4401.service;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * <b>[TODO]</b> Matrix multiplier worker.
 * Implement missing functionality in this class.
 */
public class MatrixMultiplyWorker extends AbstractServiceWorker {

    private double[][] a, b, c;
    private int mSize;
    private int iterations;

    public MatrixMultiplyWorker(MatrixMultiplyParameters parameters, MsgQ resultQ) {
        super(parameters, resultQ);
        mSize = parameters.matrixSize;
        iterations = parameters.iterations;
    }

    /**
     * Initializes the matrixes based on the size parameter.
     * <b>TODO:</b> Implement this method.
     */
    private void initMatrixes() {
        a = new double[mSize][mSize];
        b = new double[mSize][mSize];
        c = new double[mSize][mSize];
    }

    /**
     * Performs one iterarion of matrix multiplication.
     * <b>TODO:</b> Implement this method.
     */
    private void doMatrixMultiplication() {
        c = Arrays.stream(a).map(r -> IntStream.range(0, b[0].length)
                .mapToDouble(i -> IntStream.range(0, b.length)
                        .mapToDouble(j -> r[j] * b[j][i]).sum())
                .toArray()).toArray(double[][]::new);
    }

    /**
     * Invokes the initialization of source matrices and the execution of the required number of iterations.
     * The result is a <pre>Long</pre> object, which contains the execution time in milliseconds for all computations,
     * <b>without</b> the initialization.
     * <b>TODO:</b> Implement this method.
     */
    public void run() {
        initMatrixes();
        Long start = System.currentTimeMillis();
        IntStream.range(0, iterations).forEach(i -> doMatrixMultiplication());
        doMatrixMultiplication();
        Long end = System.currentTimeMillis();
        Long time = (end-start);
        resultQ.append(time);
    }

}
