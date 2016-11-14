package csci4401.service;

import java.io.Serializable;

/**
 * An implementation of the factory specialized for matrix determinant workers.
 * <b>Provided class--do not modify</b>.
 */
public class MatrixDeterminantWorkerFactory implements ServiceWorkerFactory {
    /**
     * Instantiates a new matrix determinant service worker.
     */
    public AbstractServiceWorker newServiceWorker(Serializable parameters, MsgQ resultQ) {
        return new MatrixDeterminantWorker((MatrixDeterminantParameters) parameters, resultQ);
    }
}
