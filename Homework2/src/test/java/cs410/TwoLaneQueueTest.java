package cs410;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TwoLaneQueueTest {

    @org.junit.jupiter.api.Test
    void enqueueFast() {
        TwoLaneQueue q = new TwoLaneQueue();
        // given a new String ("Brendan"), "Brendan" should be in the fast lane
        q.enqueueFast("Brendan");
        assertTrue(q.getFastLane().contains("Brendan"));
    }

    @org.junit.jupiter.api.Test
    void enqueueSlow() {
        TwoLaneQueue q = new TwoLaneQueue();
        // given a new String ("Brendan"), "Brendan" should be in the slow lane
        q.enqueueSlow("Brendan");
        assertTrue(q.getSlowLane().contains("Brendan"));
    }

    @org.junit.jupiter.api.Test
    void dequeue() {
        TwoLaneQueue q = new TwoLaneQueue();
        // given a TwoLaneQueue with two empty lanes, should throw an exception
        assertThrows(NoSuchElementException.class, q::dequeue);

        // start to fill lanes
        String[] fastLanePeople = new String[] {"Brendan", "Alberto", "Ryan", "Dennis"};
        String[] slowLanePeople = new String[] {"Don", "Robbie"};
        for (String s : fastLanePeople) {
            q.enqueueFast(s);
        }
        for (String s : slowLanePeople) {
            q.enqueueSlow(s);
        }

        // all tests below are given a TwoLaneQueue with {"Brendan", "Alberto", "Ryan", "Dennis"} in the fast lane and
        // {"Don", "Robbie"} in the slow lane

        // should return the first item in the fast lane
        assertEquals("Brendan", q.dequeue());
        // should return the first item in the fast lane
        assertEquals("Alberto", q.dequeue());
        // should return the first item in the fast lane
        assertEquals("Ryan", q.dequeue());
        // should return the first item in the slow lane
        assertEquals("Don", q.dequeue());
        // should return the first item in the fast lane
        assertEquals("Dennis", q.dequeue());
        // should return the first item in the slow lane
        assertEquals("Robbie", q.dequeue());
    }
}