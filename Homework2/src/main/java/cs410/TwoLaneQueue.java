package cs410;

import java.util.*;

public class TwoLaneQueue {

    /**
     * Object which represents the fast lane in the queue
     */
    private final LinkedList<String> fastLane;
    /**
     * Object which represents the slow lane in the queue
     */
    private final LinkedList<String> slowLane;
    /**
     * Counts the number of consecutive times the fast lane has produced an item
     */
    private int fastCounter;

    /**
     * Constructs a new two lane queue
     */
    public TwoLaneQueue() {
        fastLane = new LinkedList<>();
        slowLane = new LinkedList<>();
        fastCounter = 0;
    }

    /**
     * Getter for {@link #fastLane}
     * <p>
     * Used for testing only
     */
    public LinkedList<String> getFastLane() {
        return fastLane;
    }

    /**
     * Getter for {@link #slowLane}
     * <p>
     * Used for testing only
     */
    public LinkedList<String> getSlowLane() {
        return slowLane;
    }

    /**
     * Adds an item to the fast lane
     * @param s the item to add
     */
    public void enqueueFast(String s) {
        fastLane.add(s);
    }

    /**
     * Adds an item to the slow lane
     * @param s the item to add
     */
    public void enqueueSlow(String s) {
        slowLane.add(s);
    }

    /**
     * @return the next item to be produced from the queue. The rules of a dequeue are as follows:
     * <ol>
     *     <li>If both lanes are empty, throw a {@link NoSuchElementException}</li>
     *     <li>If the fast lane has produced three or more consecutive items and the slow lane is not empty, the slow
     *     lane will produce the next item</li>
     *     <li>If there is an item waiting in the fast lane, the fast lane will produce the next item</li>
     *     <li>If there is an item waiting in the slow lane, the slow lane will produce the next item</li>
     * </ol>
     * Everytime an item is produced from the fast lane, the field {@link #fastCounter} is incremented. If an item
     * is produced from the slow lane, the field is reset to 0.
     */
    public String dequeue() throws NoSuchElementException {
        // this must come first as removing from an empty list would result in a NoSuchElementException
        if (fastLane.isEmpty() && slowLane.isEmpty()) {
            throw new NoSuchElementException("Both lanes are empty");
        // this must come next as this dictates the order of producing items from the queues
        } else if (fastCounter >= 3 && !slowLane.isEmpty()) {
            fastCounter = 0;
            return slowLane.remove();
        // the default behavior is producing an item from the fast lane
        } else if (!fastLane.isEmpty()) {
            fastCounter++;
            return fastLane.remove();
        // if all else fails, produce an item from the slow lane
        } else {
            fastCounter = 0;
            return slowLane.remove();
        }
    }
}
