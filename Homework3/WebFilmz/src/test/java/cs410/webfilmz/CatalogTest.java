package cs410.webfilmz;

/*
 *
 * ADD YOUR Catalog TESTS TO THIS FILE
 *
 */

import org.junit.jupiter.api.*;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

public class CatalogTest {

    /**
     * One way to test {@link Catalog#getRecommendationsByGenre} is creating an anonymous class
     */
    @Test
    @DisplayName("Catalog: anonymous class")
    void getRecommendationsByGenre() {
        String likedGenre = "SciFi";
        Catalog catalog = BaseCatalogTest.getCatalog();
        assertEquals(
                Set.of(catalog.findByTitle("The Terminator"), catalog.findByTitle("Inception"), catalog.findByTitle("The Martian"), catalog.findByTitle("The City of Lost Children")),
                catalog.getRecommendationsByGenre(
                        // This is called an "anonymous class expression"
                        new ILikeFilm() {
                            @Override
                            public boolean isLikedDirector(String director) {
                                return false;
                            }

                            @Override
                            public boolean isLikedGenre(String genre) {
                                return likedGenre.equals(genre);
                            }
                        }
                )
        );
    }

    /**
     * Another way to test {@link Catalog#getRecommendationsByGenre} is creating an internal private class
     */
    private class JustLikesOneGenre implements ILikeFilm {
        private final String likedGenre;
        JustLikesOneGenre(String likedGenre) { this.likedGenre = likedGenre; }

        // Do any of the films the user liked have the given
        // director/genre?
        public boolean isLikedDirector(String director) {
            return false;
        }
        public boolean isLikedGenre(String genre) {
            return this.likedGenre.equals(genre);
        }

    }

    @Test
    @DisplayName("Catalog: internal class")
    void getRecommendationsByGenre2() {
        String likedGenre = "SciFi";
        Catalog catalog = BaseCatalogTest.getCatalog();
        assertEquals(
                Set.of(catalog.findByTitle("The Terminator"), catalog.findByTitle("Inception"), catalog.findByTitle("The Martian"), catalog.findByTitle("The City of Lost Children")),
                catalog.getRecommendationsByGenre(new JustLikesOneGenre(likedGenre))
        );
    }
}
