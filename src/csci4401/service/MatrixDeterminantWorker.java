package csci4401.service;

import java.util.stream.IntStream;

/**
 * Computes the determinant of a matrix.
 *
 * @author tlmader.dev@gmail.com
 * @since 2016-11-13
 */
public class MatrixDeterminantWorker extends AbstractServiceWorker {

    private double[][] a;
    private int mSize;
    private int iterations;

    public MatrixDeterminantWorker(MatrixDeterminantParameters parameters, MsgQ resultQ) {
        super(parameters, resultQ);
        mSize = parameters.matrixSize;
        iterations = parameters.iterations;
    }

    /**
     * Initializes the matrices based on the size parameter.
     */
    private void initMatrixes() {
        a = buildMatrix();
    }

    private double[][] buildMatrix() {
        return IntStream.range(0, mSize)
                .mapToObj(x -> IntStream.range(0, mSize)
                        .mapToDouble(y -> (x + 1) * (y + 1))
                        .toArray())
                .toArray(double[][]::new);
    }

    /**
     * Performs one iteration of getting the matrix determinant.
     */
    private void doMatrixDeterminant() {
        getDeterminant(a);
    }

    /**
     * Returns the determinant of the given matrix using "Laplacian" determinant expansion by minors.
     *
     * @param matrix a two-dimensional matrix of doubles
     * @return the determinant
     */
    private double getDeterminant(double[][] matrix) {
        double determinant = 0;
        if (matrix.length == 1) {
            return (matrix[0][0]);
        }
        for (int i = 0; i < matrix.length; i++) {
            double[][] minor = new double[matrix.length - 1][matrix.length - 1];
            for (int a = 1; a < matrix.length; a++) {
                for (int b = 0; b < matrix.length; b++) {
                    if (b < i) {
                        minor[a - 1][b] = matrix[a][b];
                    } else if (b > i) {
                        minor[a - 1][b - 1] = matrix[a][b];
                    }
                }
            }
            if (i % 2 == 0) {
                determinant += matrix[0][i] * (getDeterminant(minor));
            } else {
                determinant += matrix[0][i] * (getDeterminant(minor)) * -1;
            }
        }
        return determinant;
    }

    /**
     * Invokes the initialization of source matrices and the execution of the required number of iterations.
     * The result is a <pre>Long</pre> object, which contains the execution time in milliseconds for all computations,
     * <b>without</b> the initialization.
     */
    public synchronized void run() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        initMatrixes();
        Long start = System.currentTimeMillis();
        IntStream.range(0, iterations).forEach(i -> doMatrixDeterminant());
        Long end = System.currentTimeMillis();
        Long time = (end - start);
        resultQ.append(time);
    }
}
