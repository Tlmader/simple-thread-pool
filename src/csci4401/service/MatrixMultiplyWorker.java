package csci4401.service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @see AbstractServiceWorker
 */
public class MatrixMultiplyWorker extends AbstractServiceWorker implements Serializable {

    private double[][] a, b;
    private int mSize;
    private int iterations;
    private BalancedMMServicePool pool;

    public MatrixMultiplyWorker(BalancedMMServicePool pool) {
        super(pool.resultQ);
        this.pool = pool;
    }

    public MatrixMultiplyWorker(MatrixMultiplyParameters parameters, BalancedMMServicePool pool) {
        super(parameters, pool.resultQ);
        this.pool = pool;
        mSize = parameters.matrixSize;
        iterations = parameters.iterations;
    }

    public MatrixMultiplyWorker(MatrixMultiplyParameters parameters, MsgQ resultQ) {
        super(parameters, resultQ);
        mSize = parameters.matrixSize;
        iterations = parameters.iterations;
    }

    /**
     * Initializes the matrices based on the size parameter.
     */
    private void initMatrixes() {
        a = buildMatrix();
        b = buildMatrix();
    }

    private double[][] buildMatrix() {
        return IntStream.range(0, mSize)
                .mapToObj(x -> IntStream.range(0, mSize)
                        .mapToDouble(y -> (x + 1) * (y + 1))
                        .toArray())
                .toArray(double[][]::new);
    }

    /**
     * Performs one iteration of matrix multiplication.
     */
    private void doMatrixMultiplication() {
        Arrays.stream(a)
                .map(r -> IntStream.range(0, b[0].length)
                        .mapToDouble(i -> IntStream.range(0, b.length)
                                .mapToDouble(j -> r[j] * b[j][i]).sum())
                        .toArray()).toArray(double[][]::new);
    }

    /**
     * Invokes the initialization of source matrices and the execution of the required number of iterations.
     * The result is a <pre>Long</pre> object, which contains the execution time in milliseconds for all computations,
     * <b>without</b> the initialization.
     */
    public void run() {
        initMatrixes();
        Long start = System.currentTimeMillis();
        IntStream.range(0, iterations).forEach(i -> doMatrixMultiplication());
        Long end = System.currentTimeMillis();
        Long time = (end - start);
        if (pool != null) {
            pool.addResult(time);
        } else {
            resultQ.append(time);
        }
    }

    public void setParameters(MatrixMultiplyParameters parameters) {
        mSize = parameters.matrixSize;
        iterations = parameters.iterations;
    }
}
