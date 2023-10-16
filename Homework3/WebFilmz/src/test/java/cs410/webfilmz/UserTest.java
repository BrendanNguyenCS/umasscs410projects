package cs410.webfilmz;

/*
 *
 * ADD YOUR User TESTS TO THIS FILE
 *
 */

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void getRecommendationsByGenre() {
        Catalog catalog = BaseCatalogTest.getCatalog();
        User alice = new User();
        Film terminator = catalog.findByTitle("The Terminator");
        alice.addWatched(terminator);
        alice.addLiked(terminator);
        assertEquals(
                Set.of(catalog.findByTitle("The City of Lost Children"), catalog.findByTitle("Inception"), catalog.findByTitle("The Martian")),
                alice.getRecommendationsByGenre(catalog)
        );
    }

    @Test
    void getAllRecommendations() {
        Catalog catalog = BaseCatalogTest.getCatalog();

        // create users
        User alice = new User();
        User brendan = new User();
        User ryan = new User();

        // create films
        Film terminator = catalog.findByTitle("The Terminator");
        Film inception = catalog.findByTitle("Inception");
        Film oppenheimer = catalog.findByTitle("Oppenheimer");

        // add films watched (3 for Terminator, 2 for Inception, 1 for Oppenheimer)
        alice.addWatched(terminator);
        alice.addWatched(inception);
        brendan.addWatched(terminator);
        brendan.addWatched(inception);
        brendan.addWatched(oppenheimer);
        ryan.addWatched(terminator);

        // add films liked (3 for Terminator, 2 for Inception, 1 for Oppenheimer)
        alice.addLiked(terminator);
        alice.addLiked(inception);
        brendan.addLiked(terminator);
        brendan.addLiked(inception);
        brendan.addLiked(oppenheimer);
        ryan.addWatched(terminator);
        ryan.addLiked(terminator);

        Map<String, Set<Film>> resultMap = ryan.getAllRecommendations(catalog, 3);

        // Check that all mappings exist and are filled (was used for debugging)
        assertFalse(resultMap.get("New Releases").isEmpty());
        assertFalse(resultMap.get("Favorite Directors").isEmpty());
        assertFalse(resultMap.get("Favorite Genres").isEmpty());
        assertFalse(resultMap.get("Most Watched").isEmpty());
        assertFalse(resultMap.get("Most Liked").isEmpty());
    }

    @Test
    void getAllRecommendationsDirector() {
        Catalog catalog = BaseCatalogTest.getCatalog();
        User alice = new User();
        Film terminator = catalog.findByTitle("The Terminator");
        alice.addWatched(terminator);
        alice.addLiked(terminator);
        assertEquals(Set.of(catalog.findByTitle("Titanic")),
                     alice.getAllRecommendations(catalog, 3).get("Favorite Directors"));
    }

    @Test
    void getAllRecommendationsGenre() {
        Catalog catalog = BaseCatalogTest.getCatalog();
        User alice = new User();
        Film terminator = catalog.findByTitle("The Terminator");
        alice.addWatched(terminator);
        alice.addLiked(terminator);
        assertEquals(Set.of(catalog.findByTitle("The City of Lost Children"), catalog.findByTitle("Inception"), catalog.findByTitle("The Martian")),
                     alice.getAllRecommendations(catalog, 3).get("Favorite Genres"));
    }

    @Test
    void getAllRecommendationsMostWatched() {
        Catalog catalog = BaseCatalogTest.getCatalog();

        // create users
        User alice = new User();
        User brendan = new User();
        User ryan = new User();

        // create films
        Film terminator = catalog.findByTitle("The Terminator");
        Film inception = catalog.findByTitle("Inception");
        Film oppenheimer = catalog.findByTitle("Oppenheimer");

        // add films watched (3 for Terminator, 2 for Inception, 1 for Oppenheimer)
        alice.addWatched(terminator);
        alice.addWatched(inception);
        brendan.addWatched(terminator);
        brendan.addWatched(inception);
        brendan.addWatched(oppenheimer);
        ryan.addWatched(terminator);

        assertEquals(
                Set.of(inception, oppenheimer),
                ryan.getAllRecommendations(catalog, 3).get("Most Watched")
        );
    }

    @Test
    void getAllRecommendationsMostLiked() {
        Catalog catalog = BaseCatalogTest.getCatalog();

        // create users
        User alice = new User();
        User brendan = new User();
        User ryan = new User();

        // create films
        Film terminator = catalog.findByTitle("The Terminator");
        Film inception = catalog.findByTitle("Inception");
        Film oppenheimer = catalog.findByTitle("Oppenheimer");

        // add films liked (3 for Terminator, 2 for Inception, 1 for Oppenheimer)
        alice.addLiked(terminator);
        alice.addLiked(inception);
        brendan.addLiked(terminator);
        brendan.addLiked(inception);
        brendan.addLiked(oppenheimer);
        ryan.addWatched(terminator);
        ryan.addLiked(terminator);

        assertEquals(
                Set.of(inception, oppenheimer),
                ryan.getAllRecommendations(catalog, 3).get("Most Liked")
        );
    }

    @Test
    void isLikedGenre() {
        Catalog catalog = BaseCatalogTest.getCatalog();
        User alice = new User();
        Film amelie = catalog.findByTitle("Amelie");
        alice.addWatched(amelie);
        alice.addLiked(amelie);
        assertTrue(alice.isLikedGenre("Romance"));
        assertFalse(alice.isLikedDirector("Thriller"));
    }
}