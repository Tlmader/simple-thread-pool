package csci4401.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * A more advanced implementaion of a service pool for matrix multiplication workers,
 * which matches the number of outstanding jobs to the number of available hardware-supported threads.
 */
public class ReusableMMServicePool extends BalancedMMServicePool {

    ArrayList<ReusableMMWorker> workers = new ArrayList<>();

    /**
     * @param poolMin minimum pool size
     * @param poolMax maximum pool size
     */
    public ReusableMMServicePool(int poolMin, int poolMax) {
        super(poolMin, poolMax);
        IntStream.range(0, poolMax).forEach(i -> workers.add(factory.newServiceWorker(this)));
        workers.forEach(Thread::start);
    }

    @Override
    public void addRequest(Serializable request) {
        ReusableMMWorker worker = workers.remove(0);
        worker.addRequest(request);
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
