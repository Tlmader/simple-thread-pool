package csci4401.service;

import java.io.Serializable;

/**
 * @author tlmader.dev@gmail.com
 * @see AbstractServiceWorker
 * @since 11/28/2016
 */
public class ReusableMMWorker extends MatrixMultiplyWorker {

    private MsgQ queue = new BasicMsgQ();

    public ReusableMMWorker(BalancedMMServicePool pool) {
        super(new MatrixMultiplyParameters(0, 0), pool);
    }

    /**
     * Invokes the initialization of source matrices and the execution of the required number of iterations.
     * The result is a <pre>Long</pre> object, which contains the execution time in milliseconds for all computations,
     * <b>without</b> the initialization.
     */
    @Override
    public void run() {
        while (true) {
            MatrixMultiplyParameters parameters = null;
            try {
                parameters = (MatrixMultiplyParameters) queue.pop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mSize = parameters.matrixSize;
            iterations = parameters.iterations;
            doComputation();
        }
    }

    public void addRequest(Serializable parameters) {
        queue.append(parameters);
    }
}
