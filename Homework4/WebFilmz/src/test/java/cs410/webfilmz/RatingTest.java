package cs410.webfilmz;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class RatingTest {

    @Test
    @DisplayName("Rating: isAppropriateFor")
    void isAppropriateFor() {
        // PG-13 films are appropriate for an audience of max rating R
        assertTrue(Rating.PG13.isAppropriateFor(Rating.R));
        // PG-13 films are not appropriate for an audience of max rating PG
        assertFalse(Rating.PG13.isAppropriateFor(Rating.PG));
        // PG-13 films are appropriate for an audience of max rating PG-13
        assertTrue(Rating.PG13.isAppropriateFor(Rating.PG13));
    }

    @Test
    @DisplayName("Rating: toString")
    void toStringTest() {
        assertEquals("G", Rating.G.toString());
    }
}
