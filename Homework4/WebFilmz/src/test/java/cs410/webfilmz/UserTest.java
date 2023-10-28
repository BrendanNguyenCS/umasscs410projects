package cs410.webfilmz;

/*
 *
 * ADD YOUR User TESTS TO THIS FILE
 *
 */

import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    @DisplayName("Debug: all recommendations")
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

    @Nested
    @DisplayName("User: tests without rating")
    class UserTestWithoutRating {

        @Test
        @DisplayName("recommendations by new releases")
        void getAllRecommendationsRating() {
            Catalog catalog = BaseCatalogTest.getCatalog();
            User alice = new User();

            // Appropriate films
            Film inception = catalog.findByTitle("Inception");
            Film martian = catalog.findByTitle("The Martian");
            Film memento = catalog.findByTitle("Memento");
            Film oppenheimer = catalog.findByTitle("Oppenheimer");
            Film amelie = catalog.findByTitle("Amelie");
            Film titanic = catalog.findByTitle("Titanic");

            assertEquals(Set.of(inception, martian, memento, oppenheimer, amelie),
                         alice.getAllRecommendations(catalog, 5).get("New Releases"));
            assertEquals(Set.of(inception, martian, memento, oppenheimer, amelie, titanic),
                         alice.getAllRecommendations(catalog, 6).get("New Releases"));
        }

        @Test
        @DisplayName("recommendations by genre map")
        void getAllRecommendationsByGenre() {
            Catalog catalog = BaseCatalogTest.getCatalog();
            User alice = new User();
            Film terminator = catalog.findByTitle("The Terminator");
            alice.addWatched(terminator);
            alice.addLiked(terminator);
            Film lostChildren = catalog.findByTitle("The City of Lost Children");
            Film inception = catalog.findByTitle("Inception");
            Film martian = catalog.findByTitle("The Martian");
            assertEquals(
                    Set.of(lostChildren, inception, martian),
                    alice.getAllRecommendations(catalog, 3).get("Favorite Genres")
            );
        }

        @Test
        @DisplayName("recommendations by director map")
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
        @DisplayName("recommendations by most watched map")
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
        @DisplayName("recommendations by  most liked map")
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
    }

    @Nested
    @DisplayName("User: tests with rating restriction")
    class UserTestWithRating {

        @Test
        @DisplayName("recommendations by new releases")
        void getAllRecommendationsRating() {
            Catalog catalog = BaseCatalogTest.getCatalog();
            User alice = new User(Rating.PG13);

            // Appropriate films
            Film inception = catalog.findByTitle("Inception");
            Film martian = catalog.findByTitle("The Martian");
            Film titanic = catalog.findByTitle("Titanic");

            assertEquals(Set.of(inception, martian),
                         alice.getAllRecommendations(catalog, 5).get("New Releases"));
            assertEquals(Set.of(inception, martian, titanic),
                         alice.getAllRecommendations(catalog, 6).get("New Releases"));
        }

        @Test
        @DisplayName("recommendations by genre map")
        void getAllRecommendationsGenreRating() {
            Catalog catalog = BaseCatalogTest.getCatalog();
            User alice = new User(Rating.PG13);
            Film terminator = catalog.findByTitle("The Terminator");
            alice.addWatched(terminator);
            alice.addLiked(terminator);

            //
            Film inception = catalog.findByTitle("Inception");
            Film martian = catalog.findByTitle("The Martian");

            assertEquals(Set.of(inception, martian),
                         alice.getAllRecommendations(catalog, 3).get("Favorite Genres"));
        }

        @Test
        @DisplayName("recommendations by director map")
        void getAllRecommendationsDirectorRating() {
            Catalog catalog = BaseCatalogTest.getCatalog();
            User alice = new User(Rating.PG13);
            Film terminator = catalog.findByTitle("The Terminator");
            alice.addWatched(terminator);
            alice.addLiked(terminator);

            Film titanic = catalog.findByTitle("Titanic");

            // Add more films by James Cameron
            Film avatar = catalog.add("Avatar", "James Cameron", "SciFi", 2009, Rating.PG13);
            catalog.add("Aliens", "James Cameron", "SciFi", 1986, Rating.R);

            assertEquals(Set.of(titanic, avatar),
                         alice.getAllRecommendations(catalog, 4).get("Favorite Directors"));
        }

        @Test
        @DisplayName("recommendations by most watched map")
        void getAllRecommendationsMostWatchedRating() {
            Catalog catalog = BaseCatalogTest.getCatalog();

            // create users
            User alice = new User();
            User brendan = new User();
            User ryan = new User();
            User adam = new User(Rating.PG13);

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
                    Set.of(inception),
                    adam.getAllRecommendations(catalog, 3).get("Most Watched")
            );
        }

        @Test
        @DisplayName("recommendations by most liked map")
        void getAllRecommendationsMostLikedRating() {
            Catalog catalog = BaseCatalogTest.getCatalog();

            // create users
            User alice = new User();
            User brendan = new User();
            User ryan = new User();
            User adam = new User(Rating.PG13);

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
                    Set.of(inception),
                    adam.getAllRecommendations(catalog, 3).get("Most Liked")
            );
        }
    }

    @Test
    @DisplayName("User: isLikedDirector")
    void isLikedDirector() {
        Catalog catalog = BaseCatalogTest.getCatalog();
        User alice = new User();
        Film amelie = catalog.findByTitle("Amelie");
        alice.addWatched(amelie);
        alice.addLiked(amelie);
        assertTrue(alice.isLikedDirector("Jean-Pierre Jeunet"));
        assertFalse(alice.isLikedDirector("James Cameron"));
    }

    @Test
    @DisplayName("User: isLikedGenre")
    void isLikedGenre() {
        Catalog catalog = BaseCatalogTest.getCatalog();
        User alice = new User();
        Film amelie = catalog.findByTitle("Amelie");
        alice.addWatched(amelie);
        alice.addLiked(amelie);
        assertTrue(alice.isLikedGenre("Romance"));
        assertFalse(alice.isLikedGenre("Thriller"));
    }
}
