package csci4401.service;

import java.io.Serializable;

/**
 * An implementation of the factory specialized for matrix multiplication workers.
 * <b>Provided class--do not modify</b>.
 */
public class MatrixMultiplyWorkerFactory implements ServiceWorkerFactory {
    /**
     * Instantiates a new matrix multiplication service worker.
     */
    public AbstractServiceWorker newServiceWorker(Serializable parameters, MsgQ resultQ) {
        return new MatrixMultiplyWorker((MatrixMultiplyParameters) parameters, resultQ);
    }
    /**
     * Instantiates a new matrix multiplication service worker for usage with BalancedMMServicePool.
     */
    public AbstractServiceWorker newServiceWorker(Serializable parameters, BalancedMMServicePool pool) {
        return new MatrixMultiplyWorker((MatrixMultiplyParameters) parameters, pool);
    }
    /**
     * Instantiates a new matrix multiplication service worker for usage with ReusableMMServicePool.
     */
    public ReusableMMWorker newServiceWorker(ReusableMMServicePool pool) {
        return new ReusableMMWorker(pool);
    }
}
