package csci4401.service;

/**
 * Simple class (struct) to pass on parameters for the matrix multiplication computation.
 * <b>Provided class--do not modify</b>.
 */
public class MatrixDeterminantParameters implements java.io.Serializable {

    public int matrixSize, iterations;

    /**
     * @param matrixSize size of the matrix (e.g. 500)
     * @param iterations how many multiplacations should be performed.
     */
    public MatrixDeterminantParameters(int matrixSize, int iterations) {
        this.matrixSize = matrixSize;
        this.iterations = iterations;
    }

}
