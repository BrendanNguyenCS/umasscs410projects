package cs410.webfilmz;

import java.util.*;

/**
 * Represents a user (subscriber to the service), including
 * their watch/like history.
 * <p>
 * Responsible for maintaining watch/like history, expressing
 * film preferences, and getting personalized recommendations.
 * <p>
 * Refers to and updates {@link Film}.
 * <p>
 * Relies on {@link Catalog} to implement recommendations.
 */
public class User implements ILikeFilm {
    /**
     * Sets of films watched and liked, respectively.
     * <p>
     * Uses {@link Set} to avoid recording watch/like multiple times.
     */
    private final Set<Film> watched;
    private final Set<Film> liked;

    /**
     * Sets of directors and genres that are liked.
     * <p>
     * Uses {@link Set} to avoid directors/genres multiple times.
     */
    private final Set<String> likedDirectors;
    private final Set<String> likedGenres;

    /**
     * Maximum acceptable rating for recommendations.
     */
    private final Rating limitRating;

    public User(Rating limitRating) {
        this.watched = new HashSet<>();
        this.liked = new HashSet<>();
        this.limitRating = limitRating;
        this.likedDirectors = new HashSet<>();
        this.likedGenres = new HashSet<>();
    }

    public User() {
        this(Rating.R);
    }

    /**
     * Record that the user has watched/liked the given film,
     * updating the film if not already in {@link Set}.
     * @param film the film that has been watched/liked
     */
    public void addWatched(Film film) {
        if (watched.add(film)) {
            film.incrementWatched(1);
        }
    }
    public void addLiked(Film film) {
        if (liked.add(film)) {
            film.incrementLiked(1);
            likedDirectors.add(film.director());
            likedGenres.add(film.genre());
        }
    }

    /**
     * Generates a map of all of this user's personal recommendations. Includes the sets of new releases, films by their favorite directors, films in their favorite genres, the most watched films, and the most liked films. All sets are filtered out to not include films already watched by the user.
     * @param catalog the film catalog
     * @param initialGenericRecsCount the number of recommendations the catalog should generate
     */
    public Map<String, Set<Film>> getAllRecommendations(Catalog catalog, int initialGenericRecsCount) {
        // Get all sets needed for map
        Set<Film> recsByYear = getRecommendationsByYear(catalog, initialGenericRecsCount);
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
     * Generates a set of personal recommendations of films in the following categories:
     * <ul>
     *     <li>Newly released
     *     <li>Liked directors
     *     <li>Newly genres
     *     <li>Most watched
     *     <li>Most liked
     * </ul>
     * Also filters out films the user has already watched and are inappropriate for this user.
     * @param catalog the film catalog
     * @param initialGenericRecsCount the number of recommendations the catalog should generate
     */
    private Set<Film> getRecommendationsByYear(Catalog catalog, int initialGenericRecsCount) {
        Set<Film> recsByYear = catalog.getRecommendationsByYear(initialGenericRecsCount);
        return filterFilms(recsByYear);
    }
    private Set<Film> getRecommendationsByDirector(Catalog catalog) {
        Set<Film> recommendations = catalog.getRecommendationsByDirector(this);
        return filterFilms(recommendations);
    }
    private Set<Film> getRecommendationsByGenre(Catalog catalog) {
        Set<Film> recommendations = catalog.getRecommendationsByGenre(this);
        return filterFilms(recommendations);
    }
    private Set<Film> getRecommendationsMostWatched(Catalog catalog, int initialGenericRecsCount) {
        Set<Film> mostWatched = catalog.getRecommendationsMostWatched(initialGenericRecsCount);
        return filterFilms(mostWatched);
    }
    private Set<Film> getRecommendationsMostLiked(Catalog catalog, int initialGenericRecsCount) {
        Set<Film> mostLiked = catalog.getRecommendationsMostLiked(initialGenericRecsCount);
        return filterFilms(mostLiked);
    }

    /**
     * Do any of the films the user has liked have the given director?
     * @param director the desired director's name
     * @return {@code true} if this user likes the desired director, {@code false} otherwise.
     */
    public boolean isLikedDirector(String director) {
        for (String d : likedDirectors) {
            if (d.equals(director)) return true;
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
            if (g.equals(genre)) return true;
        }
        return false;
    }

    /**
     * @param films the set of films to filter through
     * @return a defensive copy of the set of films while filtering out films that have already been watched and are
     * inappropriate for this user's audience.
     */
    private Set<Film> filterFilms(Set<Film> films) {
        Set<Film> filteredFilms = new HashSet<>(films);
        filteredFilms.removeAll(watched);
        filteredFilms.removeIf(f -> !f.isAppropriateFor(limitRating));
        return filteredFilms;
    }
}
