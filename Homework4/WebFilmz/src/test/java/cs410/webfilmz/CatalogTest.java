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

    private static class JustLikesOneGenre implements ILikeFilm {
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
        assertEquals(Set.of(catalog.findByTitle("The Terminator"), catalog.findByTitle("Inception"), catalog.findByTitle("The Martian"), catalog.findByTitle("The City of Lost Children")),
                     catalog.getRecommendationsByGenre(new JustLikesOneGenre(likedGenre)));
    }
}
