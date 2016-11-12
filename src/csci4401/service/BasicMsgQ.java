package csci4401.service;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @see MsgQ
 */
public class BasicMsgQ implements MsgQ {

    private Queue<Serializable> queue = new LinkedList<>();

    /**
     * @see MsgQ#append(Serializable) ()
     */
    public void append(Serializable message) {
        queue.add(message);
        this.notify();
    }

    /**
     * @see MsgQ#pop()
     */
    public synchronized Serializable pop() throws InterruptedException {
        while(queue.size() == 0)
            this.wait();
        return queue.remove();
    }

    /**
     * @see MsgQ#asyncPop()
     */
    public Serializable asyncPop() {
        return queue.remove();
    }
}
