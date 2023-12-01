package cs410;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class DurationTest {

    @Test
    @DisplayName("Duration: static factory methods")
    void of() {
        // given 15331, should return 4:15:31
        assertEquals(Duration.of(15331), Duration.of(4, 15, 31));
        // given 4:15:31, should return 4:15:31
        assertEquals(Duration.of(4, 15, 31), Duration.of(4, 15, 31));
        // given 4:62:67, should return 5:03:07
        assertEquals(Duration.of(4, 62, 67), Duration.of(5, 3, 7));
        // given -15331, should throw an exception
        assertThrows(IllegalArgumentException.class, () -> Duration.of(-15331));
        // given -4:15:31, should throw an exception
        assertThrows(IllegalArgumentException.class, () -> Duration.of(-4, 15, 31));
        // given 4:-15:31, should throw an exception
        assertThrows(IllegalArgumentException.class, () -> Duration.of(4, -15, 31));
        // given 4:15:-31, should throw an exception
        assertThrows(IllegalArgumentException.class, () -> Duration.of(4, 15, -31));
    }

    @Test
    @DisplayName("Duration: add Duration objects")
    void add() {
        // given this = 10:09:08, other: 04:06:23, should return 14:15:31
        assertEquals(Duration.of(14, 15, 31),
                     Duration.of(10, 9, 8).add(Duration.of(4, 6, 23)));
        // given this = 36548, other = 24:53:53, should return 25:03:01
        assertEquals(Duration.of(35, 3, 1),
                     Duration.of(36548).add(Duration.of(24, 53, 53)));
        // given this = 10:09:08, other = 14783, should return 14:15:31
        assertEquals(Duration.of(14, 15, 31),
                     Duration.of(10, 9, 8).add(Duration.of(14783)));
        // given 36548, other = 14783, should return 51331
        assertEquals(Duration.of(51331),
                     Duration.of(36548).add(Duration.of(14783)));
    }

    @Test
    @DisplayName("Duration: translate to seconds")
    void seconds() {
        // given this = 10:09:08, should return 36548
        assertEquals(36548, Duration.of(10, 9, 8).seconds());
        // given this = 36548, should return 36548
        assertEquals(36548, Duration.of(36548).seconds());
    }

    @Test
    @DisplayName("Duration: string representation")
    void toStringTest() {
        // given 1:15:00, should return "1:05:00"
        assertEquals("1:15:00", Duration.of(1, 15, 0).toString());
        // given 1000000, should return "277:46:40"
        assertEquals("277:46:40", Duration.of(1000000).toString());
    }
}