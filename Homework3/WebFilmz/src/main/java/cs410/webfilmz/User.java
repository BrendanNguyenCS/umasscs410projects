package cs410.webfilmz;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* Represents a user (subscriber to the service), including
 * their watch/like history.
 * Responsible for maintaining watch/like history, expressing
 * film preferences, and getting personalized recommendations.
 * Refers to and updates Film.
 * Relies on Catalog to implement recommendations.
 */
public class User implements ILikeFilm {
    /* Sets of films watched and liked, respectively.
     * Uses Set to avoid recording watch/like multiple times.
     */
    private final Set<Film> watched;
    private final Set<Film> liked;

    /*
     * Sets of directors and genres that are liked
     * Uses Set to avoid directors/genres multiple times.
     */
    private final Set<String> likedDirectors;
    private final Set<String> likedGenres;

    public User() {
        watched = new HashSet<>();
        liked = new HashSet<>();
        likedDirectors = new HashSet<>();
        likedGenres = new HashSet<>();
    }

    // Record that the user watched/liked the given film,
    // updating film if not already in Set.
    public void addWatched(Film film) {
        if (watched.add(film)) {
            film.incrementWatched(1);
        }
    }
    public void addLiked(Film film) {
        if (liked.add(film)) {
            film.incrementLiked(1);
            likedDirectors.add(film.getDirector());
            likedGenres.add(film.getGenre());
        }
    }

    /**
     * Generates a map of all of this user's personal recommendations. Includes the sets of new releases, films by their favorite directors, films in their favorite genres, the most watched films, and the most liked films. All sets are filtered out to not include films already watched by the user.
     * @param catalog the film catalog
     * @param initialGenericRecsCount the number of recommendations the catalog should generate
     */
    public Map<String, Set<Film>> getAllRecommendations(Catalog catalog, int initialGenericRecsCount) {
        Set<Film> recsByYear = catalog.getRecommendationsByYear(initialGenericRecsCount);
        recsByYear.removeAll(watched);
        Set<Film> recsByDirector = getRecommendationsByDirector(catalog);
        Set<Film> recsByGenre = getRecommendationsByGenre(catalog);
        Set<Film> recsMostWatched = getRecommendationsMostWatched(catalog, initialGenericRecsCount);
        Set<Film> recsMostLiked = getRecommendationsMostLiked(catalog, initialGenericRecsCount);
        return Map.of(
                "New Releases",
                recsByYear,
                "Favorite Directors",
                recsByDirector,
                "Favorite Genres",
                recsByGenre,
                "Most Watched",
                recsMostWatched,
                "Most Liked",
                recsMostLiked);
    }

    /**
     * Generates a set of personal recommendations of this user's liked directors, also filters out films the user has already watched.
     * @param catalog the film catalog
     */
    public Set<Film> getRecommendationsByDirector(Catalog catalog) {
        Set<Film> recommendations = catalog.getRecommendationsByDirector(this);
        recommendations.removeAll(watched);
        return recommendations;
    }

    /**
     * Generates a set of personal recommendations of this user's liked genres, also filters out films the user has already watched.
     * @param catalog the film catalog
     */
    public Set<Film> getRecommendationsByGenre(Catalog catalog) {
        Set<Film> recommendations = catalog.getRecommendationsByGenre(this);
        recommendations.removeAll(watched);
        return recommendations;
    }

    /**
     * Generates a set of personal recommendations of the most watched films, also filters
     * out films the user has already watched.
     * @param catalog the film catalog
     * @param initialGenericRecsCount the number of recommendations the catalog should generate
     */
    private Set<Film> getRecommendationsMostWatched(Catalog catalog, int initialGenericRecsCount) {
        Set<Film> mostWatched = catalog.getRecommendationsMostWatched(initialGenericRecsCount);
        mostWatched.removeAll(watched);
        return mostWatched;
    }

    /**
     * Generates a set of personal recommendations of the most liked films, also filters
     * out films the user has already watched.
     * @param catalog the film catalog
     * @param initialGenericRecsCount the number of recommendations the catalog should generate
     * @return the set of personal recommendations of the most liked films from the catalog. The films the user has already watched will be removed from the set received from the catalog.
     */
    private Set<Film> getRecommendationsMostLiked(Catalog catalog, int initialGenericRecsCount) {
        Set<Film> mostLiked = catalog.getRecommendationsMostLiked(initialGenericRecsCount);
        mostLiked.removeAll(watched);
        return mostLiked;
    }

    /**
     * Do any of the films the user has liked have the given director?
     * @param director the desired director's name
     * @return {@code true} if this user likes the desired director, {@code false} otherwise.
     */
    public boolean isLikedDirector(String director) {
        for (String d : likedDirectors) {
            if (d.equals(director)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Do any of the films the user has liked have the given genre?
     * @param genre the desired film genre
     * @return {@code true} if this user likes the desired film genre, {@code false} otherwise.
     */
    public boolean isLikedGenre(String genre) {
        for (String g : likedGenres) {
            if (g.equals(genre)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("The films this user has watched:");
        for (Film f : watched) {
            sb.append(f.getTitle()).append("\n");
        }
        sb.append("The films this user has liked:");
        for (Film f : liked) {
            sb.append(f.getTitle()).append("\n");
        }
        return sb.toString();
    }
}
