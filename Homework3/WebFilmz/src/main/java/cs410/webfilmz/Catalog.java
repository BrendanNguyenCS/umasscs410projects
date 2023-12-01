package cs410.webfilmz;

import java.util.*;

/**
 * Represents the catalog, the list of all available films.
 * <p>
 * Caches a mapping of director to the films they directed.
 * <p>
 * Responsible for adding new films; generating recommendations, both generic
 * and personal.
 * <p>
 * Invariant: every film shows up both in allFilms set and in the set
 *   for the director of the film in {@link #byDirector} and in the set for the genre
 *   of the film in {@link #byGenre}
 * <p>
 * Refers to {@link Film}, factory for Films
 * <p>
 * Relies on {@link ILikeFilm} for film preferences for generating recommendations.
 */
public class Catalog {
    /**
     * The set of all films in the catalog.
     */
    private final Set<Film> allFilms;

    /**
     * Cached mapping from director to the films they directed.
     */
    private final Map<String, Set<Film>> byDirector;

    /**
     * Cached mapping from genre to the films in that genre.
     */
    private final Map<String, Set<Film>> byGenre;

    public Catalog() {
        allFilms = new HashSet<>();
        byDirector = new HashMap<>();
        byGenre = new HashMap<>();
    }

    /**
     * Factory for films, ensures that new films are recorded in the catalog.
     * @param title the film's title
     * @param director the film's director
     * @param genre the film's genre
     * @param releaseYear the film's release year
     * @return the new film
     */
    public Film add(String title, String director, String genre,
                    int releaseYear) {
        Film newFilm = new Film(title, director, genre, releaseYear);
        allFilms.add(newFilm);
        Set<Film> otherFilms = byDirector.get(director);
        Set<Film> otherFilmsByGenre = byGenre.get(genre);
        if (otherFilms == null) {
            otherFilms = new HashSet<>();
            byDirector.put(director, otherFilms);
        }
        if (otherFilmsByGenre == null) {
            otherFilmsByGenre = new HashSet<>();
            byGenre.put(genre, otherFilmsByGenre);
        }
        otherFilms.add(newFilm);
        otherFilmsByGenre.add(newFilm);
        return newFilm;
    }

    /**
     * @param title the film's title
     * @return the film with the given title
     * @throws RuntimeException if no such film is in the catalog
     */
    public Film findByTitle(String title) {
        for (Film film : allFilms) {
            if (film.getTitle().equals(title)) {
                return film;
            }
        }
        throw new RuntimeException("film not found");
    }

    /**
     * Get up to count recommendations, the most recent/watched/liked films in the catalog.
     * @param count the total number of recommendations to return
     * @return the set of film recommendations
     */
    public Set<Film> getRecommendationsByYear(int count) {
        Comparator<Film> comparator = Comparator.comparingInt(Film::getReleaseYear).reversed();
        return getRecommendationBySorting(count, comparator);
    }
    public Set<Film> getRecommendationsMostWatched(int count) {
        Comparator<Film> comparator = Comparator.comparingInt(Film::getTotalWatched).reversed();
        return getRecommendationBySorting(count, comparator);
    }
    public Set<Film> getRecommendationsMostLiked(int count) {
        Comparator<Film> comparator = Comparator.comparingInt(Film::getTotalLiked).reversed();
        return getRecommendationBySorting(count, comparator);
    }

    /**
     * Generalization of non-personalized recommendations by Film attributes. Comparator should
     * put best recommendations at the start of the list.
     * @param count the total number of recommendations to return
     * @param comparator the comparator to use to sort the films
     * @return the set of film recommendations
     */
    private Set<Film> getRecommendationBySorting(int count, Comparator<Film> comparator) {
        List<Film> films = new ArrayList<>(allFilms);
        films.sort(comparator);
        count = Integer.min(count, films.size());
        films = films.subList(0, count);
        return new HashSet<>(films);
    }

    /**
     * Get all films by liked director/genre
     * @param user the user to get recommendations for
     * @return the set of film recommendations
     */
    public Set<Film> getRecommendationsByDirector(ILikeFilm user) {
        Set<Film> recommendations = new HashSet<>();
        for (Map.Entry<String, Set<Film>> entry : byDirector.entrySet()) {
            if (user.isLikedDirector(entry.getKey())) {
                recommendations.addAll(entry.getValue());
            }
        }
        return recommendations;
    }
    public Set<Film> getRecommendationsByGenre(ILikeFilm user) {
        Set<Film> recommendations = new HashSet<>();
        for (Map.Entry<String, Set<Film>> entry : byGenre.entrySet()) {
            if (user.isLikedGenre(entry.getKey())) {
                recommendations.addAll(entry.getValue());
            }
        }
        return recommendations;
    }
}
