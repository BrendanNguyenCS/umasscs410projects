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

    @Test
    @DisplayName("Catalog: getRecommendationsByGenre")
    void getRecommendationsByGenre() {
        String likedGenre = "SciFi";
        Catalog catalog = BaseCatalogTest.getCatalog();
        Film terminator = catalog.findByTitle("The Terminator");
        Film inception = catalog.findByTitle("Inception");
        Film martian = catalog.findByTitle("The Martian");
        Film cityOfLostChildren = catalog.findByTitle("The City of Lost Children");
        assertEquals(
                Set.of(terminator, inception, martian, cityOfLostChildren),
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

    private class JustLikesOneGenre implements ILikeFilm {
        private final String likedGenre;
        JustLikesOneGenre(String likedGenre) { this.likedGenre = likedGenre; }

        public boolean isLikedDirector(String director) {
            return false;
        }
        public boolean isLikedGenre(String genre) {
            return this.likedGenre.equals(genre);
        }
    }

    @Test
    @DisplayName("Catalog: getRecommendationsByGenre2")
    void getRecommendationsByGenre2() {
        String likedGenre = "SciFi";
        Catalog catalog = BaseCatalogTest.getCatalog();
        Film terminator = catalog.findByTitle("The Terminator");
        Film inception = catalog.findByTitle("Inception");
        Film martian = catalog.findByTitle("The Martian");
        Film cityOfLostChildren = catalog.findByTitle("The City of Lost Children");
        assertEquals(Set.of(terminator, inception, martian, cityOfLostChildren),
                     catalog.getRecommendationsByGenre(new JustLikesOneGenre(likedGenre)));
    }
}
