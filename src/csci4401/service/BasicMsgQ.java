package csci4401.service;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @see MsgQ
 */
public class BasicMsgQ implements MsgQ {

    private Queue<Serializable> queue;

    public BasicMsgQ() {
        queue = new ConcurrentLinkedQueue<>();
    }

    /**
     * @see MsgQ#append(Serializable) ()
     */
    public synchronized void append(Serializable message) {
        queue.add(message);
        this.notify();
        System.out.println("Notified!");
    }

    /**
     * @see MsgQ#pop()
     */
    public synchronized Serializable pop() {
        while (queue.isEmpty()) {
            try {
                System.out.println("Waiting...");
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return queue.remove();
    }

    /**
     * @see MsgQ#asyncPop()
     */
    public Serializable asyncPop() {
        return queue.remove();
    }
}
