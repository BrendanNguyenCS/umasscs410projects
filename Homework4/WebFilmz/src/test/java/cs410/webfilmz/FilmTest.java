package cs410.webfilmz;

/*
 *
 * ADD YOUR Catalog TESTS TO THIS FILE
 *
 */

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class FilmTest {

    @Test
    @DisplayName("Film: isAppropriateFor")
    void testRating() {
        Catalog catalog = BaseCatalogTest.getCatalog();
        Film terminator = catalog.findByTitle("The Terminator"); // R
        Film toystory = catalog.findByTitle("Toy Story"); // G
        assertTrue(terminator.isAppropriateFor(Rating.R));
        assertFalse(terminator.isAppropriateFor(Rating.PG13));
        assertTrue(toystory.isAppropriateFor(Rating.R));
        assertTrue(toystory.isAppropriateFor(Rating.G));
    }

    @Test
    @DisplayName("Film: toString")
    void toStringTest() {
        Catalog catalog = BaseCatalogTest.getCatalog();
        Film terminator = catalog.findByTitle("The Terminator");
        String expected = "The Terminator (1984, SciFi, R) directed by James Cameron";
        assertEquals(expected, terminator.toString());
    }

    @Test
    @DisplayName("Film: getDisplay")
    void getDisplay() {
        Catalog catalog = BaseCatalogTest.getCatalog();
        Film terminator = catalog.findByTitle("The Terminator");
        assertEquals("R", terminator.getRating().getDisplay());
    }

    @Test
    @DisplayName("Film: getLevel")
    void getLevel() {
        Catalog catalog = BaseCatalogTest.getCatalog();
        Film terminator = catalog.findByTitle("The Terminator");
        assertEquals(4, terminator.getRating().getLevel());
    }
}
