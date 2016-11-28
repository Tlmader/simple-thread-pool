package csci4401.service;

import java.io.Serializable;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.IntStream;

/**
 * A more advanced implementaion of a service pool for matrix multiplication workers,
 * which matches the number of outstanding jobs to the number of available hardware-supported threads.
 */
public class ReusableMMServicePool extends BalancedMMServicePool {

    Queue<AbstractServiceWorker> workers = new PriorityQueue<>();

    /**
     * @param poolMin minimum pool size
     * @param poolMax maximum pool size
     */
    public ReusableMMServicePool(int poolMin, int poolMax) {
        super(poolMin, poolMax);
        IntStream.range(0, poolMax).forEach(i -> workers.add(factory.newServiceWorker(this)));
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
                addRequestForReusableWorker(delayedRequests.pop());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            addRequestForReusableWorker(request);
        }
    }

    private void addRequestForReusableWorker(Serializable request) {
        MatrixMultiplyWorker worker = (MatrixMultiplyWorker) workers.remove();
        worker.setParameters((MatrixMultiplyParameters) request);
        worker.start();
        workers.add(worker);
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
