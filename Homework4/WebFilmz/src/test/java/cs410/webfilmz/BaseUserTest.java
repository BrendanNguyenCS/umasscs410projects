package cs410.webfilmz;

/*
 *
 * DO NOT MAKE ANY CHANGES TO THIS FILE
 *
 * Add your own tests to UserTest.java.
 *
 */

import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BaseUserTest {

    @Test
    void addWatchedAndLiked() {
        Catalog catalog = BaseCatalogTest.getCatalog();
        User alice = new User();
        Film amelie = catalog.findByTitle("Amelie");
        Film terminator = catalog.findByTitle("The Terminator");
        assertEquals(0, amelie.getTotalWatched());
        assertEquals(0, amelie.getTotalLiked());
        assertEquals(0, terminator.getTotalWatched());
        assertEquals(0, terminator.getTotalLiked());
        alice.addWatched(amelie);
        assertEquals(1, amelie.getTotalWatched());
        assertEquals(0, amelie.getTotalLiked());
        alice.addLiked(amelie);
        assertEquals(1, amelie.getTotalWatched());
        assertEquals(1, amelie.getTotalLiked());
        assertEquals(0, terminator.getTotalWatched());
        assertEquals(0, terminator.getTotalLiked());
    }

    @Test
    void testAddWatchedIdempotent() {
        Catalog catalog = BaseCatalogTest.getCatalog();
        User alice = new User();
        Film amelie = catalog.findByTitle("Amelie");
        assertEquals(0, amelie.getTotalWatched());
        alice.addWatched(amelie);
        alice.addWatched(amelie);
        assertEquals(1, amelie.getTotalWatched());
    }

    @Test
    void getRecommendationsNewReleases() {
        Catalog catalog = BaseCatalogTest.getCatalog();
        User alice = new User();
        alice.addWatched(catalog.findByTitle("The Martian"));
        Film oppenheimer = catalog.findByTitle("Oppenheimer");
        Film johnwick4 = catalog.findByTitle("John Wick: Chapter 4");
        Film nobody = catalog.findByTitle("Nobody");
        assertEquals(Set.of(oppenheimer, johnwick4, nobody),
                alice.getAllRecommendations(catalog, 3).get("New Releases"));
    }

    @Test
    void getRecommendationsByDirector() {
        Catalog catalog = BaseCatalogTest.getCatalog();
        User alice = new User();
        Film amelie = catalog.findByTitle("Amelie");
        alice.addWatched(amelie);
        alice.addLiked(amelie);
        assertEquals(
                Set.of(catalog.findByTitle("The City of Lost Children")),
                alice.getAllRecommendations(catalog, 3).get("Favorite Directors"));
    }

    @Test
    void isLikedDirector() {
        Catalog catalog = BaseCatalogTest.getCatalog();
        User alice = new User();
        Film amelie = catalog.findByTitle("Amelie");
        alice.addWatched(amelie);
        alice.addLiked(amelie);
        assertTrue(alice.isLikedDirector("Jean-Pierre Jeunet"));
        assertFalse(alice.isLikedDirector("Ridley Scott"));
    }

    @Test
    void testRatings() {
        Catalog catalog = BaseCatalogTest.getCatalog();
        User bobby = new User(Rating.G);
        assertEquals(Set.of(catalog.findByTitle("Toy Story")),
                bobby.getAllRecommendations(catalog, 10).get("New Releases"));
    }
}
