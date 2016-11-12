package csci4401.service;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

/**
 * <b>[TODO]</b> Basic queue implementation.
 * Implement missing functionality in this class.
 */
public class BasicMsgQ implements MsgQ {

    private Queue queue = new LinkedList<Serializable>();

    /**
     * <b>TODO:</b> Implement this method as per the interface specification.
     */
    public void append(Serializable message) {
        queue.
    }

    /**
     * <b>TODO:</b> Implement this method as per the interface specification.
     */
    public Serializable pop() throws InterruptedException {
        return null;
    }

    /**
     * <b>TODO:</b> Implement this method as per the interface specification.
     */
    public Serializable asyncPop() {
        return null;
    }

}
