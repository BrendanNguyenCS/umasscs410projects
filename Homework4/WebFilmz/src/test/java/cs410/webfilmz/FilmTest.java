package cs410.webfilmz;

/*
 *
 * ADD YOUR Catalog TESTS TO THIS FILE
 *
 */

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FilmTest {

    @Test
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
    void toStringTest() {
        Catalog catalog = BaseCatalogTest.getCatalog();
        Film terminator = catalog.findByTitle("The Terminator");
        String expected = "The Terminator (1984, SciFi, R) directed by James Cameron";
        assertEquals(expected, terminator.toString());
    }
}
