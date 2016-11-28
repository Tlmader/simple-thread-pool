package csci4401.service;

import java.io.Serializable;

/**
 * A more advanced implementaion of a service pool for matrix multiplication workers,
 * which matches the number of outstanding jobs to the number of available hardware-supported threads.
 */
public class BalancedMMServicePool extends MatrixMultiplyServicePool {

    MsgQ delayedRequests = new BasicMsgQ();

    /**
     * @param poolMin minimum pool size
     * @param poolMax maximum pool size
     */
    public BalancedMMServicePool(int poolMin, int poolMax) {
        super(poolMin, poolMax);
    }

    @Override
    public synchronized void addRequest(Serializable request) {
        System.out.println(java.lang.Thread.activeCount());
        if (java.lang.Thread.activeCount() >= poolMax) {
            delayedRequests.append(request);
            while (java.lang.Thread.activeCount() >= poolMax) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                super.addRequest(delayedRequests.pop(), this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            super.addRequest(request, this);
        }
    }

    /**
     * Notifies the service pool that a worker has completed the computation with the given result.
     * If there are any outstanding requests, the first in line should be serviced.
     */
    public synchronized void addResult(Serializable result) {
        this.notify();
        resultQ.append(result);
    }
}
